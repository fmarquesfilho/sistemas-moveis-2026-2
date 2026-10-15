package br.ufrn.exemplo.tarefas

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// O ViewModel sem tela: cada evento muda o estado, e o teste lê estado.value.
class TarefasViewModelTest {

    @Test
    fun adicionarAcrescentaALimpaOTexto() {
        val vm = TarefasViewModel()
        vm.mudarTexto("Estudar Flow")
        vm.adicionar()
        assertEquals("Estudar Flow", vm.estado.value.tarefas.last().titulo)
        assertEquals("", vm.estado.value.texto)
    }

    @Test
    fun tituloEmBrancoNaoAcrescenta() {
        val vm = TarefasViewModel()
        vm.mudarTexto("   ")
        vm.adicionar()
        assertEquals(2, vm.estado.value.tarefas.size)
    }

    @Test
    fun alternarMarcaATarefa() {
        val vm = TarefasViewModel()
        vm.alternar(1)
        assertTrue(vm.estado.value.tarefas.first { it.id == 1 }.feita)
    }
}
