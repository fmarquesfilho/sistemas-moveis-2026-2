package br.ufrn.exemplo.tarefas

import br.ufrn.exemplo.tarefas.apresentacao.EstadoLista
import br.ufrn.exemplo.tarefas.apresentacao.EstadoTela
import br.ufrn.exemplo.tarefas.apresentacao.TarefasViewModel
import br.ufrn.exemplo.tarefas.apresentacao.TelaCarregando
import br.ufrn.exemplo.tarefas.apresentacao.TelaDetalhe
import br.ufrn.exemplo.tarefas.apresentacao.TelaErro
import br.ufrn.exemplo.tarefas.apresentacao.TelaLista
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import androidx.window.core.layout.WindowSizeClass
import kotlinx.serialization.Serializable

// Rotas tipadas: cada destino é um tipo, e os argumentos são propriedades.
@Serializable
object Lista

@Serializable
data class Detalhe(val id: Int)

@Composable
fun App() {
    // Largura da janela: compacta (celular em pé) ou média/expandida (tablet, desktop).
    val largura = currentWindowAdaptiveInfo().windowSizeClass
    val largo = largura.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    // O tema segue o sistema. `MaterialTheme { }` sem `colorScheme` é sempre claro.
    val cores = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()

    // O Koin fica disponível para tudo o que está dentro do bloco (21/10).
    KoinApplication(application = { modules(moduloTarefas) }) {
        MaterialTheme(colorScheme = cores) {
            // Surface pinta o fundo do tema (claro/escuro); safeDrawingPadding evita a barra
            // de status e o recorte da câmera no Android.
            Surface(Modifier.fillMaxSize()) {
                Conteudo(largo = largo, modifier = Modifier.safeDrawingPadding())
            }
        }
    }
}

// O estado mora no ViewModel, acima da navegação: as duas telas enxergam a mesma lista.
// `largo` e o ViewModel vêm de fora para que os testes escolham o layout e o estado.
@Composable
fun Conteudo(
    largo: Boolean,
    modifier: Modifier = Modifier,
    viewModel: TarefasViewModel = koinViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    // collectAsStateWithLifecycle: lê o StateFlow como estado do Compose, e para de
    // coletar quando a tela não está visível.
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var selecionada by rememberSaveable { mutableStateOf<Int?>(null) }   // estado só da interface

    if (largo) {
        // Janela larga: lista e detalhe lado a lado, sem navegar.
        Row(modifier.fillMaxSize()) {
            ListaConformeEstado(
                estado, viewModel,
                onAbrir = { selecionada = it },
                modifier = Modifier.width(360.dp),
            )
            VerticalDivider()
            TelaDetalhe(
                tarefa = estado.tarefas.find { it.id == selecionada },
                onAlternar = { selecionada?.let(viewModel::alternar) },
                onVoltar = null,
                modifier = Modifier.fillMaxHeight(),
            )
        }
    } else {
        // Janela compacta: uma tela por vez, com pilha de retorno.
        NavHost(navController, startDestination = Lista, modifier = modifier) {
            composable<Lista> {
                ListaConformeEstado(
                    estado, viewModel,
                    onAbrir = { id -> navController.navigate(Detalhe(id)) },
                )
            }
            composable<Detalhe>(
                // tarefas://tarefa/2 abre direto o detalhe da tarefa 2 (Android).
                deepLinks = listOf(navDeepLink<Detalhe>(basePath = "tarefas://tarefa")),
            ) { entrada ->
                val rota = entrada.toRoute<Detalhe>()
                TelaDetalhe(
                    tarefa = estado.tarefas.find { it.id == rota.id },
                    onAlternar = { viewModel.alternar(rota.id) },
                    onVoltar = { navController.popBackStack() },
                )
            }
        }
    }
}

// Um ramo por caso da classe selada. Sem `else`: se surgir um quinto estado, o `when`
// deixa de compilar até que alguém decida como mostrá-lo.
@Composable
fun ListaConformeEstado(
    estado: EstadoLista,
    viewModel: TarefasViewModel,
    onAbrir: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (val tela = estado.tela) {
        EstadoTela.Carregando -> TelaCarregando(modifier)
        is EstadoTela.Erro -> TelaErro(tela.mensagem, onTentarDeNovo = viewModel::carregar, modifier = modifier)
        EstadoTela.Vazio, is EstadoTela.Sucesso -> TelaLista(
            estado.tarefas, estado.texto, viewModel::mudarTexto, viewModel::adicionar, viewModel::alternar,
            onAbrir = onAbrir,
            modifier = modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}
