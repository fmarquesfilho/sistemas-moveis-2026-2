package br.ufrn.exemplo.tarefas.apresentacao

import br.ufrn.exemplo.tarefas.dominio.Tarefa
import br.ufrn.exemplo.tarefas.dominio.alternando
import br.ufrn.exemplo.tarefas.dominio.comNova
import br.ufrn.exemplo.tarefas.dominio.tituloValido
import br.ufrn.exemplo.tarefas.dominio.FalhaNoRepositorio
import br.ufrn.exemplo.tarefas.dominio.RepositorioDeTarefas
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Os estados possíveis da lista, como uma classe selada: o compilador conhece todos os
// casos, e um `when` que esquecer um deles não compila.
sealed interface EstadoTela {
    data object Carregando : EstadoTela
    data object Vazio : EstadoTela
    data class Sucesso(val tarefas: List<Tarefa>) : EstadoTela
    data class Erro(val mensagem: String) : EstadoTela
}

// O estado da tela de lista: o conteúdo (um dos quatro casos), o texto do formulário e
// um aviso passageiro (quando salvar falha, a lista continua na tela).
data class EstadoLista(
    val tela: EstadoTela = EstadoTela.Carregando,
    val texto: String = "",
    val aviso: String? = null,
) {
    val tarefas: List<Tarefa> get() = (tela as? EstadoTela.Sucesso)?.tarefas.orEmpty()
}

// O ViewModel guarda o estado fora da composição: sobrevive à rotação da tela e à troca
// de destino na navegação. O repositório chega pelo construtor, entregue pelo Koin.
class TarefasViewModel(
    private val repositorio: RepositorioDeTarefas,
) : ViewModel() {

    // MutableStateFlow é privado: só o ViewModel altera. A tela enxerga um StateFlow.
    private val _estado = MutableStateFlow(EstadoLista())
    val estado: StateFlow<EstadoLista> = _estado.asStateFlow()

    init {
        carregar()
    }

    // viewModelScope: as corrotinas são canceladas quando o ViewModel deixa de existir.
    fun carregar() {
        _estado.update { it.copy(tela = EstadoTela.Carregando) }
        viewModelScope.launch {
            val tela = try {
                val tarefas = repositorio.listar()
                if (tarefas.isEmpty()) EstadoTela.Vazio else EstadoTela.Sucesso(tarefas)
            } catch (e: FalhaNoRepositorio.SemConexao) {
                EstadoTela.Erro("Sem conexão com o servidor. Confira a rede e tente de novo.")
            } catch (e: FalhaNoRepositorio.RespostaInvalida) {
                EstadoTela.Erro("O servidor respondeu algo inesperado (${e.message}).")
            }
            _estado.update { it.copy(tela = tela) }
        }
    }

    fun mudarTexto(texto: String) = _estado.update { it.copy(texto = texto) }

    fun adicionar() {
        val texto = _estado.value.texto
        if (!tituloValido(texto)) return
        viewModelScope.launch {
            try {
                val nova = repositorio.adicionar(texto)
                _estado.update { it.copy(tela = EstadoTela.Sucesso(it.tarefas + nova), texto = "", aviso = null) }
            } catch (e: FalhaNoRepositorio) {
                _estado.update { it.copy(aviso = "A tarefa não foi salva: ${e.message}.") }
            }
        }
    }

    // Marcar como feita é só local neste exemplo: a API de Tarefas não tem atualização.
    fun alternar(id: Int) = _estado.update {
        if (it.tela is EstadoTela.Sucesso) it.copy(tela = EstadoTela.Sucesso(it.tarefas.alternando(id))) else it
    }
}
