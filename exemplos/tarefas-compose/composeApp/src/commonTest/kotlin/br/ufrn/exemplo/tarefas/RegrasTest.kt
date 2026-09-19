package br.ufrn.exemplo.tarefas

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

// Regras puras: kotlin.test, sem Compose. Rodam em todos os alvos.
class RegrasTest {

    @Test
    fun tituloEmBrancoNaoEValido() {
        assertFalse(tituloValido(""))
        assertFalse(tituloValido("   "))
        assertTrue(tituloValido("Ler"))
    }

    @Test
    fun novaTarefaEntraNoFimSemEspacosNasPontas() {
        val lista = tarefasIniciais.comNova(3, "  Revisar  ")
        assertEquals(Tarefa(3, "Revisar"), lista.last())
        assertEquals(3, lista.size)
    }

    @Test
    fun alternarMudaSoATarefaDoId() {
        val lista = tarefasIniciais.alternando(1)
        assertTrue(lista.first { it.id == 1 }.feita)
        assertFalse(lista.first { it.id == 2 }.feita)
    }
}
