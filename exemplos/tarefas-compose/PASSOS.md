# Passos — tela de Tarefas em Compose

Uma tela construída **do zero, incrementalmente**, em Compose Multiplatform. Cada passo
roda. **Esta pasta já é um projeto KMP completo** (alvos **Android** e **Desktop**); a UI
fica em `composeApp/src/commonMain/kotlin/.../App.kt` e é a **mesma** nos dois alvos.

## Como abrir e rodar

O projeto usa AGP 9.1: abre no **Android Studio Panda 2 (2025.3.2) ou mais novo**. O
Android Studio do laboratório (Ladybug, 2024) **não sincroniza** este projeto; lá, use o
Codespaces e instale o APK no emulador (abaixo).

**No Codespaces** (nada instalado na máquina): crie um Codespace deste repositório
(Code → Codespaces). O `.devcontainer/` já traz Java, o SDK Android e uma área de
trabalho no navegador (aba **Portas** → **6080**, senha `vscode`).

- **`@Preview` no Android Studio:** no terminal, `bash .devcontainer/android-studio.sh`;
  na área de trabalho, abra `App.kt` e clique **Split**.
- **App com Hot Reload, em janela de celular:**

  ```bash
  cd exemplos/tarefas-compose
  ./gradlew :composeApp:hotDevDesktop --auto --className=br.ufrn.exemplo.tarefas.PreviewCelularKt --funName=TelaCelular
  ```

  Ao salvar um arquivo, a janela atualiza em segundos.

**No emulador ou no celular, via APK:** `./gradlew :composeApp:assembleDebug` gera
`composeApp/build/outputs/apk/debug/composeApp-debug.apk`. Baixe (botão direito →
Download) e arraste para a janela do emulador — funciona em qualquer versão do Android
Studio — ou instale com `adb install -r composeApp-debug.apk`. A chave de debug fica em
`keystore/`, então um APK de qualquer máquina atualiza o de outra. Se o Android acusar
`signatures do not match`, desinstale uma vez: `adb uninstall br.ufrn.exemplo.tarefas`.

**No Android Studio local (Panda 2 ou mais novo):** abra a pasta `tarefas-compose/`,
deixe o Gradle sincronizar, escolha **composeApp** e o emulador e clique **Run ▶**.
Desktop: `./gradlew :composeApp:hotRunDesktop --auto`.

A UI compartilhada fica em `composeApp/src/commonMain/kotlin` — é lá que entram os passos
abaixo. `App()` é montada pelo `MainActivity` (Android) e pelo `main()` (desktop).

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
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AppPreview() { App() }
```

> O `@Preview` é desenhado pela IDE: aparece no Android Studio (local ou no Codespaces,
> pelo `android-studio.sh`), não no VS Code. O equivalente com Hot Reload é
> `@DevelopmentEntryPoint`, em `desktopMain/.../PreviewCelular.kt`.

---

## Onde isto encosta no MUSI

`@Composable`, estado elevado, componente próprio e `LazyColumn` são as mesmas peças da
pasta `app/` do MUSI — aqui isoladas, numa tela só.
