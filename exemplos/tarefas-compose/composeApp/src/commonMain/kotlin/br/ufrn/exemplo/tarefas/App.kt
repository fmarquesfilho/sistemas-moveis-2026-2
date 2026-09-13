package br.ufrn.exemplo.tarefas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

// Modelo de UI. Esta MESMA UI roda no Android quando colocada em commonMain.
data class Tarefa(val id: Int, val titulo: String, val feita: Boolean = false)

@Composable
fun App() {
    MaterialTheme {
        // Estado ELEVADO: a tela guarda a lista e o texto do formulário.
        var tarefas by remember {
            mutableStateOf(
                listOf(
                    Tarefa(1, "Estudar Compose"),
                    Tarefa(2, "Entender estado elevado"),
                ),
            )
        }
        var texto by remember { mutableStateOf("") }
        var proximoId by remember { mutableStateOf(3) }

        val tituloValido = texto.isNotBlank()

        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text("Minhas tarefas", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))

            FormularioTarefa(
                texto = texto,
                onTextoChange = { texto = it },
                valido = tituloValido,
                onAdicionar = {
                    tarefas = tarefas + Tarefa(proximoId, texto.trim())
                    proximoId++
                    texto = ""
                },
            )
            Spacer(Modifier.height(12.dp))

            // LazyColumn: só cria os itens visíveis. `key` estabiliza a rolagem.
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(tarefas, key = { it.id }) { tarefa ->
                    CartaoTarefa(
                        tarefa = tarefa,
                        onAlternar = {
                            tarefas = tarefas.map {
                                if (it.id == tarefa.id) it.copy(feita = !it.feita) else it
                            }
                        },
                    )
                }
            }
        }
    }
}

// Componente próprio e reutilizável, com estado elevado:
// recebe o que mostra (tarefa) e devolve o evento (onAlternar), sem estado interno.
@Composable
fun CartaoTarefa(tarefa: Tarefa, onAlternar: () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(checked = tarefa.feita, onCheckedChange = { onAlternar() })
            Spacer(Modifier.width(8.dp))
            Text(tarefa.titulo)
        }
    }
}

// Formulário com validação simples: o botão só habilita com título válido.
@Composable
fun FormularioTarefa(
    texto: String,
    onTextoChange: (String) -> Unit,
    valido: Boolean,
    onAdicionar: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = texto,
            onValueChange = onTextoChange,
            label = { Text("Nova tarefa") },
            isError = texto.isNotEmpty() && !valido,
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.width(8.dp))
        Button(onClick = onAdicionar, enabled = valido) { Text("Adicionar") }
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}
