package br.ufrn.exemplo.tarefas

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// O modelo da tela. Imutável: mudar uma tarefa é criar outra (`copy`).
data class Tarefa(val id: Int, val titulo: String, val feita: Boolean = false)

@Composable
fun App() {
    // O tema segue o sistema. `MaterialTheme { }` sem `colorScheme` é sempre claro.
    val cores = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = cores) {
        // Surface pinta o fundo e dá ao texto a cor do tema (sem ela, o fundo não escurece);
        // safeDrawingPadding afasta o conteúdo da barra de status e do recorte da câmera.
        Surface(Modifier.fillMaxSize()) {
            // O estado da lista fica aqui em cima; o cartão só recebe e avisa.
            var tarefas by remember {
                mutableStateOf(listOf(Tarefa(1, "Estudar Compose"), Tarefa(2, "Entender estado elevado")))
            }

            Column(Modifier.safeDrawingPadding().padding(16.dp)) {
                Text("Minhas tarefas", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))

                var texto by remember { mutableStateOf("") }
                var proximoId by remember { mutableStateOf(3) }

                // Derivado do estado, não guardado: recalculado a cada recomposição.
                val valido = texto.isNotBlank()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = texto,
                        onValueChange = { texto = it },
                        label = { Text("Nova tarefa") },
                        isError = texto.isNotEmpty() && !valido,
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            tarefas = tarefas + Tarefa(proximoId, texto.trim())
                            proximoId++
                            texto = ""
                        },
                        enabled = valido,
                    ) { Text("Adicionar") }
                }
                Spacer(Modifier.height(12.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(tarefas, key = { it.id }) { tarefa ->
                        CartaoTarefa(tarefa) {
                            tarefas = tarefas.map { if (it.id == tarefa.id) it.copy(feita = !it.feita) else it }
                        }
                    }
                }
            }
        }
    }
}

// COMPONENTE PRÓPRIO: recebe o que mostra e devolve o evento. Não guarda estado.
@Composable
fun CartaoTarefa(tarefa: Tarefa, onAlternar: () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = tarefa.feita, onCheckedChange = { onAlternar() })
            Spacer(Modifier.width(8.dp))
            Text(tarefa.titulo)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}
