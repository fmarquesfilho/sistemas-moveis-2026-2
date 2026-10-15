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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration

// Testes de interface: montam a tela, agem como o usuário e conferem a árvore de semântica
// (a mesma que os leitores de tela usam). Rodam no alvo desktop: ./gradlew :composeApp:desktopTest
@OptIn(ExperimentalTestApi::class, ExperimentalCoroutinesApi::class)
class TelasTest {

    // O ViewModel e a navegação usam Dispatchers.Main. Nos testes, ele é trocado por um
    // despachante de teste, que executa na hora, na thread do próprio teste.
    @BeforeTest
    fun trocarMain() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @AfterTest
    fun restaurarMain() = Dispatchers.resetMain()

    @Test
    fun adicionaTarefaPeloFormulario() = runComposeUiTest {
        setContent { Conteudo(largo = false, viewModel = TarefasViewModel(RepositorioEmMemoria(demora = Duration.ZERO))) }
        onNodeWithText("Adicionar").assertIsNotEnabled()
        onNodeWithText("Nova tarefa").performTextInput("Escrever testes")
        onNodeWithText("Adicionar").performClick()
        onNodeWithText("Escrever testes").assertIsDisplayed()
    }

    @Test
    fun linhaInteiraEUmaCaixaDeSelecao() = runComposeUiTest {
        setContent { Conteudo(largo = false, viewModel = TarefasViewModel(RepositorioEmMemoria(demora = Duration.ZERO))) }
        onNodeWithText("Estudar Compose").assertIsToggleable().assertIsOff()
        onNodeWithText("Estudar Compose").performClick()
        onNodeWithText("Estudar Compose").assertIsOn()
    }

    @Test
    fun tituloEAnunciadoComoCabecalho() = runComposeUiTest {
        setContent { Conteudo(largo = false, viewModel = TarefasViewModel(RepositorioEmMemoria(demora = Duration.ZERO))) }
        assertEquals(1, onAllNodes(isHeading()).fetchSemanticsNodes().size)
    }

    @Test
    fun compactaNavegaParaODetalheEVolta() = runComposeUiTest {
        setContent { Conteudo(largo = false, viewModel = TarefasViewModel(RepositorioEmMemoria(demora = Duration.ZERO))) }
        onAllNodesWithText("Detalhes")[0].performClick()
        onNodeWithText("Situação: pendente").assertIsDisplayed()
        onNodeWithText("Marcar como feita").performClick()
        onNodeWithText("Situação: feita").assertIsDisplayed()
        onNodeWithText("Voltar").performClick()
        onNodeWithText("Estudar Compose").assertIsOn()
    }

    @Test
    fun largaMostraListaEDetalheLadoALado() = runComposeUiTest {
        setContent { Conteudo(largo = true, viewModel = TarefasViewModel(RepositorioEmMemoria(demora = Duration.ZERO))) }
        onNodeWithText("Selecione uma tarefa").assertIsDisplayed()
        onAllNodesWithText("Detalhes")[1].performClick()
        onNodeWithText("Situação: pendente").assertIsDisplayed()
        onNodeWithText("Nova tarefa").assertIsDisplayed() // a lista continua na tela
    }

    @Test
    fun erroMostraMensagemEBotaoDeTentarDeNovo() = runComposeUiTest {
        val repositorio = RepositorioEmMemoria(demora = Duration.ZERO, falhar = true)
        setContent { Conteudo(largo = false, viewModel = TarefasViewModel(repositorio)) }
        onNodeWithText("Não foi possível carregar as tarefas.").assertIsDisplayed()
        repositorio.falhar = false
        onNodeWithText("Tentar de novo").performClick()
        onNodeWithText("Estudar Compose").assertIsDisplayed()
    }
}
