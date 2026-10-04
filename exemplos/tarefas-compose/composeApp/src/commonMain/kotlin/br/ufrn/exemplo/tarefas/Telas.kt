package br.ufrn.exemplo.tarefas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

// Lista + formulário. Não guarda estado: recebe tudo e devolve eventos (estado elevado).
@Composable
fun TelaLista(
    tarefas: List<Tarefa>,
    texto: String,
    onTextoChange: (String) -> Unit,
    onAdicionar: () -> Unit,
    onAlternar: (Int) -> Unit,
    onAbrir: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(16.dp)) {
        // heading(): leitores de tela anunciam como título e permitem pular entre títulos.
        Text(
            "Minhas tarefas",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.semantics { heading() },
        )
        Spacer(Modifier.height(12.dp))
        FormularioTarefa(texto, onTextoChange, tituloValido(texto), onAdicionar)
        Spacer(Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(tarefas, key = { it.id }) { tarefa ->
                CartaoTarefa(tarefa, onAlternar = { onAlternar(tarefa.id) }, onAbrir = { onAbrir(tarefa.id) })
            }
        }
    }
}

// Linha inteira alternável: um único elemento para o leitor de tela (papel de caixa de
// seleção, com o texto e o estado), com área de toque do tamanho da linha.
@Composable
fun CartaoTarefa(tarefa: Tarefa, onAlternar: () -> Unit, onAbrir: () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                Modifier
                    .weight(1f)
                    .toggleable(value = tarefa.feita, role = Role.Checkbox, onValueChange = { onAlternar() })
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = tarefa.feita, onCheckedChange = null) // o clique é da linha
                Spacer(Modifier.width(8.dp))
                Text(tarefa.titulo)
            }
            TextButton(onClick = onAbrir) { Text("Detalhes") }
        }
    }
}

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

// Detalhe de uma tarefa. `onVoltar` nulo: não há para onde voltar (painel lateral).
@Composable
fun TelaDetalhe(
    tarefa: Tarefa?,
    onAlternar: () -> Unit,
    onVoltar: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(16.dp)) {
        if (onVoltar != null) {
            TextButton(onClick = onVoltar) { Text("Voltar") }
        }
        if (tarefa == null) {
            Text("Selecione uma tarefa")
            return@Column
        }
        Text(
            tarefa.titulo,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.semantics { heading() },
        )
        Spacer(Modifier.height(8.dp))
        Text(if (tarefa.feita) "Situação: feita" else "Situação: pendente")
        Spacer(Modifier.height(16.dp))
        Button(onClick = onAlternar) {
            Text(if (tarefa.feita) "Marcar como pendente" else "Marcar como feita")
        }
    }
}

// Previews: dados fixos, eventos vazios.
@Preview(showBackground = true)
@Composable
fun TelaListaPreview() {
    TelaLista(tarefasIniciais, "", {}, {}, {}, {})
}

@Preview(showBackground = true)
@Composable
fun CartaoTarefaPreview() {
    CartaoTarefa(Tarefa(1, "Estudar Compose", feita = true), onAlternar = {}, onAbrir = {})
}

@Preview(showBackground = true)
@Composable
fun TelaDetalhePreview() {
    TelaDetalhe(Tarefa(1, "Estudar Compose"), onAlternar = {}, onVoltar = {})
}
