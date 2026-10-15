package br.ufrn.exemplo.tarefas.apresentacao

import br.ufrn.exemplo.tarefas.dados.RepositorioEmMemoria
import br.ufrn.exemplo.tarefas.dominio.Tarefa
import br.ufrn.exemplo.tarefas.dominio.tarefasIniciais

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.time.Duration

// O ViewModel sem tela. viewModelScope usa Dispatchers.Main; no teste, ele é trocado por
// um despachante que executa as corrotinas na hora.
@OptIn(ExperimentalCoroutinesApi::class)
class TarefasViewModelTest {

    @BeforeTest
    fun trocarMain() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @AfterTest
    fun restaurarMain() = Dispatchers.resetMain()

    private fun vm(falhar: Boolean = false, iniciais: List<Tarefa> = tarefasIniciais) =
        TarefasViewModel(RepositorioEmMemoria(Duration.ZERO, falhar, iniciais))

    @Test
    fun carregaAsTarefas() {
        assertEquals(2, assertIs<EstadoTela.Sucesso>(vm().estado.value.tela).tarefas.size)
    }

    @Test
    fun listaVaziaViraVazio() {
        assertEquals(EstadoTela.Vazio, vm(iniciais = emptyList()).estado.value.tela)
    }

    @Test
    fun falhaViraErro() {
        assertIs<EstadoTela.Erro>(vm(falhar = true).estado.value.tela)
    }

    @Test
    fun adicionarAcrescentaELimpaOTexto() {
        val vm = vm()
        vm.mudarTexto("Estudar Flow")
        vm.adicionar()
        assertEquals("Estudar Flow", vm.estado.value.tarefas.last().titulo)
        assertEquals("", vm.estado.value.texto)
    }

    @Test
    fun tituloEmBrancoNaoAcrescenta() {
        val vm = vm()
        vm.mudarTexto("   ")
        vm.adicionar()
        assertEquals(2, vm.estado.value.tarefas.size)
    }

    @Test
    fun alternarMarcaATarefa() {
        val vm = vm()
        vm.alternar(1)
        assertTrue(vm.estado.value.tarefas.first { it.id == 1 }.feita)
    }
}
