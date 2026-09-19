package br.ufrn.exemplo.tarefas

import androidx.compose.runtime.Composable
import org.jetbrains.compose.reload.DevelopmentEntryPoint

// Preview com Hot Reload: abre SÓ esta função numa janela do tamanho de um celular.
// ./gradlew :composeApp:hotDevDesktop --auto \
//   --className=br.ufrn.exemplo.tarefas.PreviewCelularKt --funName=TelaCelular
@DevelopmentEntryPoint(windowWidth = 412, windowHeight = 915)
@Composable
fun TelaCelular() {
    App()
}
