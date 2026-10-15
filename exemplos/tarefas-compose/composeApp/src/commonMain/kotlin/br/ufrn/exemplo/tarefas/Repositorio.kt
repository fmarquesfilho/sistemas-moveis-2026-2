package br.ufrn.exemplo.tarefas

import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

// De onde vêm as tarefas. A tela e o ViewModel conhecem só esta interface; quem a
// implementa (memória, rede, banco local) é escolhido de fora.
interface RepositorioDeTarefas {
    // suspend: a resposta pode demorar (rede, disco), e a função espera sem travar a tela.
    suspend fun listar(): List<Tarefa>
    suspend fun adicionar(titulo: String): Tarefa
}

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
