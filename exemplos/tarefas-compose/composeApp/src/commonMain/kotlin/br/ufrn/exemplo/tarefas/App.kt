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

// O estado da tela, elevado: TelaLista só recebe dados e devolve eventos.
@Composable
fun Conteudo(modifier: Modifier = Modifier) {
    var tarefas by remember { mutableStateOf(tarefasIniciais) }
    var texto by rememberSaveable { mutableStateOf("") }
    var proximoId by remember { mutableStateOf(3) }

    TelaLista(
        tarefas, texto, { texto = it },
        onAdicionar = {
            tarefas = tarefas.comNova(proximoId, texto)
            proximoId++
            texto = ""
        },
        onAlternar = { id -> tarefas = tarefas.alternando(id) },
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}
