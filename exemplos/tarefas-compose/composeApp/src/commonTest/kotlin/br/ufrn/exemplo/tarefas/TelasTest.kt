package br.ufrn.exemplo.tarefas

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsToggleable
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertEquals

// Testes de interface: montam a tela, agem como o usuário e conferem a árvore de semântica
// (a mesma que os leitores de tela usam). Rodam no alvo desktop: ./gradlew :composeApp:desktopTest
@OptIn(ExperimentalTestApi::class)
class TelasTest {

    @Test
    fun adicionaTarefaPeloFormulario() = runComposeUiTest {
        setContent { Conteudo(largo = false) }
        onNodeWithText("Adicionar").assertIsNotEnabled()
        onNodeWithText("Nova tarefa").performTextInput("Escrever testes")
        onNodeWithText("Adicionar").performClick()
        onNodeWithText("Escrever testes").assertIsDisplayed()
    }

    @Test
    fun linhaInteiraEUmaCaixaDeSelecao() = runComposeUiTest {
        setContent { Conteudo(largo = false) }
        onNodeWithText("Estudar Compose").assertIsToggleable().assertIsOff()
        onNodeWithText("Estudar Compose").performClick()
        onNodeWithText("Estudar Compose").assertIsOn()
    }

    @Test
    fun tituloEAnunciadoComoCabecalho() = runComposeUiTest {
        setContent { Conteudo(largo = false) }
        assertEquals(1, onAllNodes(isHeading()).fetchSemanticsNodes().size)
    }

    @Test
    fun compactaNavegaParaODetalheEVolta() = runComposeUiTest {
        setContent { Conteudo(largo = false) }
        onAllNodesWithText("Detalhes")[0].performClick()
        onNodeWithText("Situação: pendente").assertIsDisplayed()
        onNodeWithText("Marcar como feita").performClick()
        onNodeWithText("Situação: feita").assertIsDisplayed()
        onNodeWithText("Voltar").performClick()
        onNodeWithText("Estudar Compose").assertIsOn()
    }

    @Test
    fun largaMostraListaEDetalheLadoALado() = runComposeUiTest {
        setContent { Conteudo(largo = true) }
        onNodeWithText("Selecione uma tarefa").assertIsDisplayed()
        onAllNodesWithText("Detalhes")[1].performClick()
        onNodeWithText("Situação: pendente").assertIsDisplayed()
        onNodeWithText("Nova tarefa").assertIsDisplayed() // a lista continua na tela
    }
}
