package br.ufrn.exemplo.tarefas.dados

import br.ufrn.exemplo.tarefas.dominio.FalhaNoRepositorio
import br.ufrn.exemplo.tarefas.dominio.Listagem
import br.ufrn.exemplo.tarefas.dominio.RepositorioDeTarefas
import br.ufrn.exemplo.tarefas.dominio.Tarefa
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.ContentConvertException
import io.ktor.serialization.kotlinx.json.json
import kotlinx.io.IOException
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

// DTO: o formato do JSON da API, separado da entidade do domínio. Se a API mudar um nome
// de campo, muda o DTO e a conversão; o domínio e as telas continuam iguais.
@Serializable
data class TarefaDto(val id: Int, val titulo: String, val feita: Boolean = false)

@Serializable
data class NovaTarefaDto(val titulo: String)

fun TarefaDto.paraDominio() = Tarefa(id = id, titulo = titulo, feita = feita)

// O repositório que fala com a API de Tarefas de Web II (GET e POST /tarefas).
class RepositorioRemoto(
    private val cliente: HttpClient,
    private val enderecoDaApi: String,
) : RepositorioDeTarefas {

    override suspend fun listar(): Listagem = traduzindoFalhas {
        Listagem(cliente.get("$enderecoDaApi/tarefas").body<List<TarefaDto>>().map { it.paraDominio() })
    }

    override suspend fun adicionar(titulo: String): Tarefa = traduzindoFalhas {
        cliente.post("$enderecoDaApi/tarefas") {
            contentType(ContentType.Application.Json)
            setBody(NovaTarefaDto(titulo))
        }.body<TarefaDto>().paraDominio()
    }

    // Exceções da biblioteca viram falhas do domínio: o ViewModel não precisa conhecer o Ktor.
    private suspend fun <T> traduzindoFalhas(chamada: suspend () -> T): T = try {
        chamada()
    } catch (e: IOException) {
        throw FalhaNoRepositorio.SemConexao()             // servidor fora, sem rede, recusado
    } catch (e: ResponseException) {
        throw FalhaNoRepositorio.RespostaInvalida("status ${e.response.status.value}")
    } catch (e: ContentConvertException) {
        throw FalhaNoRepositorio.RespostaInvalida("JSON fora do formato")
    } catch (e: SerializationException) {
        throw FalhaNoRepositorio.RespostaInvalida("JSON fora do formato")
    }
}

// O cliente HTTP. Sem motor explícito: cada plataforma usa o que estiver no classpath
// (OkHttp no Android, CIO no desktop). expectSuccess faz 4xx e 5xx virarem exceção.
fun criarClienteHttp(): HttpClient = HttpClient {
    expectSuccess = true
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })   // campos novos na API não quebram o app
    }
}
