package br.ufrn.exemplo.tarefas

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

// Ponto de entrada do alvo DESKTOP. No projeto KMP do Android Studio, o Android tem
// seu próprio ponto de entrada (MainActivity) e o desktop usa este mesmo padrão.
// A tela — App() — é a mesma nos dois. A janela abre no tamanho de um celular.
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tarefas",
        state = rememberWindowState(size = DpSize(412.dp, 915.dp)),
    ) {
        App()
    }
}
