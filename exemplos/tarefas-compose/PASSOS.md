# Passos — tela de Tarefas em Compose

Uma tela construída **do zero, incrementalmente**, em Compose Multiplatform. Cada passo
roda. Esta pasta é o alvo **Desktop** (para o ciclo rápido); a UI (`App.kt`) é a
**mesma** que roda no Android quando fica em `commonMain`.

## Ponto de partida limpo (em aula, no Android Studio)

Usamos o **Android Studio** (instalado no laboratório) — não o Codespaces, porque
Hot Reload e `@Preview` funcionam bem localmente.

1. **New Project → Kotlin Multiplatform** (ou gere em `kmp.jetbrains.com`)
2. Alvos: **Android** e **Desktop**; opção **Share UI** marcada
3. Rodar:
   - **androidApp** no emulador, **ou**
   - **desktopApp [hot] 🔥** — inclui **Compose Hot Reload**
4. A UI compartilhada fica em **`composeApp/src/commonMain/kotlin`** (nome do módulo
   pode ser `shared/`, conforme o assistente). É lá que entram os passos abaixo.

> Para reconstruir só o Desktop fora do Android Studio: `./gradlew run` nesta pasta.

---

## Passo 1 — um `@Composable` com estado

```kotlin
@Composable
fun App() {
  MaterialTheme {
    var contador by remember { mutableStateOf(0) }
    Button(onClick = { contador++ }) { Text("Cliquei $contador") }
  }
}
```

> **Fundamento (fecha a Sprint 0).** `remember { mutableStateOf(...) }` guarda estado
> entre recomposições. Clicar muda o estado → o Compose **recompõe** o que depende dele.

---

## Passo 2 — componente próprio, com estado elevado

```kotlin
data class Tarefa(val id: Int, val titulo: String, val feita: Boolean = false)

@Composable
fun CartaoTarefa(tarefa: Tarefa, onAlternar: () -> Unit) {
  Card(Modifier.fillMaxWidth()) {
    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
      Checkbox(checked = tarefa.feita, onCheckedChange = { onAlternar() })
      Spacer(Modifier.width(8.dp)); Text(tarefa.titulo)
    }
  }
}
```

> **Fundamento.** O componente **recebe** o que mostra (`tarefa`) e **devolve** o
> evento (`onAlternar`), sem guardar estado próprio. É o *state hoisting*.

---

## Passo 3 — lista com `LazyColumn`

```kotlin
LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
  items(tarefas, key = { it.id }) { tarefa ->
    CartaoTarefa(tarefa) { /* alterna 'feita' */ }
  }
}
```

> **Fundamento.** `LazyColumn` só cria os itens **visíveis** — desempenho em listas
> grandes. `key` estabiliza a rolagem quando a lista muda.

---

## Passo 4 — formulário com validação

```kotlin
var texto by remember { mutableStateOf("") }
val valido = texto.isNotBlank()

Row {
  OutlinedTextField(
    value = texto, onValueChange = { texto = it },
    label = { Text("Nova tarefa") },
    isError = texto.isNotEmpty() && !valido,
    modifier = Modifier.weight(1f),
  )
  Button(onClick = { /* adiciona */ }, enabled = valido) { Text("Adicionar") }
}
```

> **Fundamento.** A validação é derivada do estado (`valido`); a UI reage — o botão
> só habilita quando o título é válido.

---

## Passo 5 — Material 3 e tema

`MaterialTheme { ... }` já dá o esquema de cor, tipografia e componentes (Material 3).
No modo claro/escuro:

```kotlin
MaterialTheme(colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()) {
  App()
}
```

> **Fundamento.** Componentes (`Button`, `Card`, `OutlinedTextField`) já seguem o tema.
> Cor e tipografia vêm do `MaterialTheme`, não são fixadas à mão.

---

## `@Preview` (no Android Studio)

Em `commonMain`, anote uma função sem parâmetros para ver a tela sem rodar o app:

```kotlin
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AppPreview() { App() }
```

> No Codespaces o `@Preview` e o Hot Reload não funcionam bem — por isso a aula é no
> Android Studio do laboratório.

---

## Onde isto encosta no MUSI

`@Composable`, estado elevado, componente próprio e `LazyColumn` são as mesmas peças da
pasta `app/` do MUSI — aqui isoladas, numa tela só.
