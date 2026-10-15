package br.ufrn.exemplo.tarefas.dados

import br.ufrn.exemplo.tarefas.dominio.FalhaNoRepositorio
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

// O repositório contra um servidor HTTP de mentira (MockEngine): sem rede, sem a API.
class RepositorioRemotoTest {

    private fun repositorio(resposta: MockEngine) = RepositorioRemoto(
        HttpClient(resposta) {
            expectSuccess = true
            install(ContentNegotiation) { json() }
        },
        enderecoDaApi = "http://api",
    )

    private val json = headersOf(HttpHeaders.ContentType, "application/json")

    @Test
    fun converteOJsonDaApiEmTarefas() = runTest {
        val repo = repositorio(MockEngine { respond("""[{"id":1,"titulo":"Estudar Ktor","feita":true}]""", headers = json) })
        val tarefas = repo.listar().tarefas
        assertEquals("Estudar Ktor", tarefas.single().titulo)
        assertEquals(true, tarefas.single().feita)
    }

    @Test
    fun servidorForaViraSemConexao() = runTest {
        val repo = repositorio(MockEngine { throw IOException("conexão recusada") })
        assertFailsWith<FalhaNoRepositorio.SemConexao> { repo.listar() }
    }

    @Test
    fun erro500ViraRespostaInvalida() = runTest {
        val repo = repositorio(MockEngine { respond("falhou", HttpStatusCode.InternalServerError) })
        assertFailsWith<FalhaNoRepositorio.RespostaInvalida> { repo.listar() }
    }

    @Test
    fun jsonForaDoFormatoViraRespostaInvalida() = runTest {
        val repo = repositorio(MockEngine { respond("""{"nada":"de lista"}""", headers = json) })
        assertFailsWith<FalhaNoRepositorio.RespostaInvalida> { repo.listar() }
    }
}
