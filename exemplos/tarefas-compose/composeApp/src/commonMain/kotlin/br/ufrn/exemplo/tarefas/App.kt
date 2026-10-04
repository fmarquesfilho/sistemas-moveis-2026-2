package br.ufrn.exemplo.tarefas

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

// Rotas tipadas: cada destino é um tipo, e os argumentos são propriedades.
@Serializable
object Lista

@Serializable
data class Detalhe(val id: Int)

@Composable
fun App() {
    // O tema segue o sistema. `MaterialTheme { }` sem `colorScheme` é sempre claro.
    val cores = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = cores) {
        // Surface pinta o fundo do tema (claro/escuro); safeDrawingPadding evita a barra
        // de status e o recorte da câmera no Android.
        Surface(Modifier.fillMaxSize()) {
            Conteudo(modifier = Modifier.safeDrawingPadding())
        }
    }
}

// Estado elevado acima da navegação: as duas telas enxergam a mesma lista.
@Composable
fun Conteudo(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    var tarefas by remember { mutableStateOf(tarefasIniciais) }
    var texto by rememberSaveable { mutableStateOf("") }
    var proximoId by remember { mutableStateOf(3) }

    val adicionar = {
        tarefas = tarefas.comNova(proximoId, texto)
        proximoId++
        texto = ""
    }
    val alternar = { id: Int -> tarefas = tarefas.alternando(id) }

    NavHost(navController, startDestination = Lista, modifier = modifier) {
        composable<Lista> {
            TelaLista(
                tarefas, texto, { texto = it }, adicionar, alternar,
                onAbrir = { id -> navController.navigate(Detalhe(id)) },
            )
        }
        composable<Detalhe>(
            // tarefas://tarefa/2 abre direto o detalhe da tarefa 2 (Android).
            deepLinks = listOf(navDeepLink<Detalhe>(basePath = "tarefas://tarefa")),
        ) { entrada ->
            val rota = entrada.toRoute<Detalhe>()
            TelaDetalhe(
                tarefa = tarefas.find { it.id == rota.id },
                onAlternar = { alternar(rota.id) },
                onVoltar = { navController.popBackStack() },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}
