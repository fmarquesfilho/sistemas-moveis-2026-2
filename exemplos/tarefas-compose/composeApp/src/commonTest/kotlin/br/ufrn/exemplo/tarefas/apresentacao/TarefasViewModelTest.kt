package br.ufrn.exemplo.tarefas.apresentacao

import app.cash.turbine.test
import br.ufrn.exemplo.tarefas.dominio.FalhaNoRepositorio
import br.ufrn.exemplo.tarefas.dominio.Listagem
import br.ufrn.exemplo.tarefas.dominio.RepositorioDeTarefas
import br.ufrn.exemplo.tarefas.dominio.Tarefa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

// Dublê do repositório: responde o que o teste mandar, depois de 500 ms de "rede".
// O tempo é virtual: o teste não espera de verdade.
class RepositorioFalso(
    var listar: () -> Listagem = { Listagem(listOf(Tarefa(1, "Estudar Flow"))) },
) : RepositorioDeTarefas {
    var proximoId = 10
    override suspend fun listar(): Listagem {
        delay(500)
        return listar.invoke()
    }
    override suspend fun adicionar(titulo: String): Tarefa {
        delay(500)
        return Tarefa(proximoId++, titulo)
    }
}

// O ViewModel observado como um Flow, com o Turbine: cada awaitItem() é o próximo estado
// emitido, na ordem. Assim o teste vê também os estados intermediários (Carregando).
@OptIn(ExperimentalCoroutinesApi::class)
class TarefasViewModelTest {

    // viewModelScope usa Dispatchers.Main; aqui ele vira um despachante de teste que
    // compartilha o relógio virtual do runTest.
    private fun TestScope.usarRelogioDoTeste() = Dispatchers.setMain(StandardTestDispatcher(testScheduler))

    @AfterTest
    fun restaurarMain() = Dispatchers.resetMain()

    @Test
    fun carregarPassaPorCarregandoEChegaASucesso() = runTest {
        usarRelogioDoTeste()
        TarefasViewModel(RepositorioFalso()).estado.test {
            assertEquals(EstadoTela.Carregando, awaitItem().tela)
            val tela = assertIs<EstadoTela.Sucesso>(awaitItem().tela)
            assertEquals("Estudar Flow", tela.tarefas.single().titulo)
        }
    }

    @Test
    fun listaVaziaViraVazio() = runTest {
        usarRelogioDoTeste()
        TarefasViewModel(RepositorioFalso { Listagem(emptyList()) }).estado.test {
            assertEquals(EstadoTela.Carregando, awaitItem().tela)
            assertEquals(EstadoTela.Vazio, awaitItem().tela)
        }
    }

    @Test
    fun semConexaoViraErroComMensagemPropria() = runTest {
        usarRelogioDoTeste()
        TarefasViewModel(RepositorioFalso { throw FalhaNoRepositorio.SemConexao() }).estado.test {
            assertEquals(EstadoTela.Carregando, awaitItem().tela)
            val erro = assertIs<EstadoTela.Erro>(awaitItem().tela)
            assertTrue("Sem conexão" in erro.mensagem)
        }
    }

    @Test
    fun respostaInvalidaViraOutroErro() = runTest {
        usarRelogioDoTeste()
        TarefasViewModel(RepositorioFalso { throw FalhaNoRepositorio.RespostaInvalida("status 500") }).estado.test {
            assertEquals(EstadoTela.Carregando, awaitItem().tela)
            val erro = assertIs<EstadoTela.Erro>(awaitItem().tela)
            assertTrue("inesperado" in erro.mensagem)
        }
    }

    @Test
    fun tentarDeNovoDepoisDoErro() = runTest {
        usarRelogioDoTeste()
        val repositorio = RepositorioFalso { throw FalhaNoRepositorio.SemConexao() }
        val vm = TarefasViewModel(repositorio)
        vm.estado.test {
            skipItems(1)                                         // Carregando
            assertIs<EstadoTela.Erro>(awaitItem().tela)
            repositorio.listar = { Listagem(listOf(Tarefa(1, "Voltou"))) }
            vm.carregar()
            assertEquals(EstadoTela.Carregando, awaitItem().tela)
            assertIs<EstadoTela.Sucesso>(awaitItem().tela)
        }
    }

    @Test
    fun listaDoCacheChegaMarcada() = runTest {
        usarRelogioDoTeste()
        TarefasViewModel(RepositorioFalso { Listagem(listOf(Tarefa(1, "Guardada")), doCache = true) }).estado.test {
            skipItems(1)
            assertTrue(assertIs<EstadoTela.Sucesso>(awaitItem().tela).doCache)
        }
    }

    @Test
    fun adicionarAcrescentaELimpaOTexto() = runTest {
        usarRelogioDoTeste()
        val vm = TarefasViewModel(RepositorioFalso())
        vm.estado.test {
            skipItems(2)                                         // Carregando, Sucesso
            vm.mudarTexto("Escrever testes")
            assertEquals("Escrever testes", awaitItem().texto)
            vm.adicionar()
            val depois = awaitItem()
            assertEquals("Escrever testes", depois.tarefas.last().titulo)
            assertEquals("", depois.texto)
        }
    }
}
