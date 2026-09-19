package br.ufrn.exemplo.tarefas

// Modelo de UI. Esta MESMA UI roda no Android e no desktop, porque está em commonMain.
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
