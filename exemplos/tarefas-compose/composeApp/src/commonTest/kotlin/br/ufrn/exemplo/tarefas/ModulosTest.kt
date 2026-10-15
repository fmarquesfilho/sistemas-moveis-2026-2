package br.ufrn.exemplo.tarefas

import br.ufrn.exemplo.tarefas.apresentacao.TarefasViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.io.files.SystemTemporaryDirectory
import org.koin.dsl.koinApplication
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

// O módulo do Koin consegue montar o ViewModel? Se faltar uma definição, o teste acusa
// aqui, e não com o app aberto.
@OptIn(ExperimentalCoroutinesApi::class)
class ModulosTest {

    @BeforeTest
    fun trocarMain() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @AfterTest
    fun restaurarMain() = Dispatchers.resetMain()

    @Test
    fun oModuloMontaOViewModel() {
        val koin = koinApplication { modules(moduloTarefas(SystemTemporaryDirectory.toString())) }.koin
        assertNotNull(koin.get<TarefasViewModel>())
        koin.close()
    }
}
