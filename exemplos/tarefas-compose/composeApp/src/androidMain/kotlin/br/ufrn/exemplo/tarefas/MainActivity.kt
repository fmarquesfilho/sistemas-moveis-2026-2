package br.ufrn.exemplo.tarefas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

// Ponto de entrada do alvo Android. O emulador abre esta Activity, que apenas
// monta a tela compartilhada `App()` (a mesma do Desktop, em commonMain).
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}
