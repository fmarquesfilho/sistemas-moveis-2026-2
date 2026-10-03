package br.ufrn.exemplo.tarefas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

// Ponto de entrada do alvo Android. O emulador abre esta Activity, que apenas
// monta a tela compartilhada `App()` (a mesma do Desktop, em commonMain).
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Desenha de ponta a ponta e escolhe a cor dos ícones da barra de status pelo tema
        // do sistema: escuros no modo claro, claros no modo escuro.
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}
