package br.ufrn.exemplo.tarefas.dominio

// O domínio: o modelo e as regras, em Kotlin puro. Não importa Compose, Ktor nem banco.
data class Tarefa(val id: Int, val titulo: String, val feita: Boolean = false)

// Regras da tela como funções puras: sem Compose, testáveis com kotlin.test (21/09).
fun tituloValido(texto: String): Boolean = texto.isNotBlank()

fun List<Tarefa>.comNova(id: Int, titulo: String): List<Tarefa> = this + Tarefa(id, titulo.trim())

fun List<Tarefa>.alternando(id: Int): List<Tarefa> =
    map { if (it.id == id) it.copy(feita = !it.feita) else it }

val tarefasIniciais = listOf(
    Tarefa(1, "Estudar Compose"),
    Tarefa(2, "Entender estado elevado"),
)

// De onde vêm as tarefas. A tela e o ViewModel conhecem só esta interface; quem a
// implementa (memória, rede, banco local) é escolhido de fora.
interface RepositorioDeTarefas {
    // suspend: a resposta pode demorar (rede, disco), e a função espera sem travar a tela.
    suspend fun listar(): List<Tarefa>
    suspend fun adicionar(titulo: String): Tarefa
}

// Por que a lista não veio. Cada caso vira uma mensagem diferente na tela.
sealed class FalhaNoRepositorio(mensagem: String) : Exception(mensagem) {
    class SemConexao : FalhaNoRepositorio("sem conexão com o servidor")
    class RespostaInvalida(detalhe: String) : FalhaNoRepositorio("resposta inválida: $detalhe")
}
