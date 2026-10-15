package br.ufrn.exemplo.tarefas

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// O estado da tela de lista, num objeto só e imutável. A tela lê; quem muda é o ViewModel.
data class EstadoLista(
    val tarefas: List<Tarefa> = tarefasIniciais,
    val texto: String = "",
)

// O ViewModel guarda o estado fora da composição: sobrevive à rotação da tela e à troca
// de destino na navegação. A tela recebe o estado e chama funções para os eventos.
class TarefasViewModel : ViewModel() {

    // MutableStateFlow é privado: só o ViewModel altera. A tela enxerga um StateFlow.
    private val _estado = MutableStateFlow(EstadoLista())
    val estado: StateFlow<EstadoLista> = _estado.asStateFlow()

    private var proximoId = 3

    fun mudarTexto(texto: String) = _estado.update { it.copy(texto = texto) }

    fun adicionar() = _estado.update {
        if (!tituloValido(it.texto)) it
        else it.copy(tarefas = it.tarefas.comNova(proximoId++, it.texto), texto = "")
    }

    fun alternar(id: Int) = _estado.update { it.copy(tarefas = it.tarefas.alternando(id)) }
}
