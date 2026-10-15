package br.ufrn.exemplo.tarefas.dados

import br.ufrn.exemplo.tarefas.dominio.RepositorioDeTarefas
import br.ufrn.exemplo.tarefas.dominio.Tarefa
import br.ufrn.exemplo.tarefas.dominio.tarefasIniciais
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

// Implementação em memória, com uma demora para imitar a rede. `falhar` simula a rede fora.
class RepositorioEmMemoria(
    private val demora: Duration = 800.milliseconds,
    var falhar: Boolean = false,
    iniciais: List<Tarefa> = tarefasIniciais,
) : RepositorioDeTarefas {
    private val tarefas = iniciais.toMutableList()
    private var proximoId = (iniciais.maxOfOrNull { it.id } ?: 0) + 1

    override suspend fun listar(): List<Tarefa> {
        delay(demora)          // suspende a corrotina; a thread da interface continua livre
        if (falhar) error("sem conexão")
        return tarefas.toList()
    }

    override suspend fun adicionar(titulo: String): Tarefa {
        delay(demora)
        if (falhar) error("sem conexão")
        return Tarefa(proximoId++, titulo.trim()).also { tarefas += it }
    }
}
