# Passos — tela de Tarefas em Compose

Uma tela construída **do zero, incrementalmente**, em Compose Multiplatform. Cada passo
roda. Passos 1 a 5: aula de 14/09. Passos 6 a 9: aula de 21/09 (a partir do Passo 6 o
código fica dividido em `Tarefa.kt`, `Telas.kt` e `App.kt`). **Esta pasta já é um projeto KMP completo** (alvos **Android** e **Desktop**); a UI
fica em `composeApp/src/commonMain/kotlin/.../App.kt` e é a **mesma** nos dois alvos.

## Como abrir e rodar

O projeto usa AGP 9.1: abre no **Android Studio Panda 2 (2025.3.2) ou mais novo**. O
Android Studio do laboratório (Ladybug, 2024) **não sincroniza** este projeto; lá, use o
Codespaces e instale o APK no emulador (abaixo).

**No Codespaces** (nada instalado na máquina): crie um Codespace deste repositório
(Code → Codespaces). O `.devcontainer/` já traz Java, o SDK Android e uma área de
trabalho no navegador (aba **Portas** → **6080**, senha `vscode`).

- **`@Preview` no Android Studio:** no terminal, `bash .devcontainer/android-studio.sh`;
  na área de trabalho, abra `Telas.kt` e clique **Split**.
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

## Passo 6 — Acessibilidade: a linha inteira é a caixa de seleção

```kotlin
Row(
    Modifier
        .toggleable(value = tarefa.feita, role = Role.Checkbox, onValueChange = { onAlternar() })
        .padding(12.dp),
) {
    Checkbox(checked = tarefa.feita, onCheckedChange = null) // o clique é da linha
    Text(tarefa.titulo)
}
// e no título da tela:
Text("Minhas tarefas", modifier = Modifier.semantics { heading() })
```

> Antes, caixa e texto eram **dois** elementos para o leitor de tela, e a área de toque
> era só a caixa. Agora a linha é **um** elemento, com papel de caixa de seleção, o texto e
> o estado, tocável de ponta a ponta. `heading()` deixa pular de título em título.

📖 [Compose — Accessibility](https://developer.android.com/develop/ui/compose/accessibility)

---

## Passo 7 — Navegação com rotas tipadas

```kotlin
@Serializable object Lista
@Serializable data class Detalhe(val id: Int)

NavHost(navController, startDestination = Lista) {
    composable<Lista> { TelaLista(/* ... */ onAbrir = { id -> navController.navigate(Detalhe(id)) }) }
    composable<Detalhe>(deepLinks = listOf(navDeepLink<Detalhe>(basePath = "tarefas://tarefa"))) {
        val rota = it.toRoute<Detalhe>()
        TelaDetalhe(tarefas.find { t -> t.id == rota.id }, /* ... */ onVoltar = { navController.popBackStack() })
    }
}
```

> A lista fica **acima** do `NavHost` (estado elevado): as duas telas veem os mesmos dados.
> Deep link no Android: `adb shell am start -a android.intent.action.VIEW -d "tarefas://tarefa/2"`
> (o `AndroidManifest.xml` declara o esquema `tarefas`).

📖 [Navigation in Compose Multiplatform](https://kotlinlang.org/docs/multiplatform/compose-navigation.html)

---

## Passo 8 — Janela compacta × larga

```kotlin
val largo = currentWindowAdaptiveInfo().windowSizeClass
    .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

if (largo) Row { TelaLista(/* ... */); VerticalDivider(); TelaDetalhe(/* ... */) }
else NavHost(/* Passo 7 */)
```

> Celular em pé: uma tela por vez. Celular deitado, tablet ou janela desktop larga (600 dp
> ou mais): lista e detalhe lado a lado. Teste girando o emulador ou redimensionando a
> janela desktop.

📖 [Compose — Window size classes](https://developer.android.com/develop/ui/compose/layouts/adaptive/use-window-size-classes)

---

## Passo 9 — Testes: `kotlin.test` e interface

```kotlin
// commonTest/RegrasTest.kt — funções puras, sem Compose
@Test fun tituloEmBrancoNaoEValido() { assertFalse(tituloValido("   ")) }

// commonTest/TelasTest.kt — monta a tela e age como o usuário
@Test fun linhaInteiraEUmaCaixaDeSelecao() = runComposeUiTest {
    setContent { Conteudo(largo = false) }
    onNodeWithText("Estudar Compose").assertIsToggleable().assertIsOff()
    onNodeWithText("Estudar Compose").performClick()
    onNodeWithText("Estudar Compose").assertIsOn()
}
```

Rodar: `./gradlew :composeApp:desktopTest`.

> O teste de interface consulta a mesma árvore de semântica que o leitor de tela usa: se
> o Passo 6 for desfeito, `assertIsToggleable()` falha.

📖 [Testing Compose Multiplatform UI](https://kotlinlang.org/docs/multiplatform/compose-test.html)

---

## Passo 10 — o ambiente em tasks

Os comandos destes passos viram nomes curtos no [`mise.toml`](../../mise.toml) da raiz do
repositório, que também fixa a versão do JDK:

```toml
[tools]
java = "temurin-21"

[tasks.app]
description = "Abre a tela no desktop, com Hot Reload (recarrega ao salvar)"
dir = "exemplos/tarefas-compose"
run = '''
if [ -n "$CODESPACES" ]; then
  DISPLAY=:1 ./gradlew :composeApp:hotRunDesktop --auto
else
  ./gradlew :composeApp:hotRunDesktop --auto
fi
'''
```

```bash
mise install            # uma vez: o JDK
mise tasks              # a lista

mise run app            # == ./gradlew :composeApp:hotRunDesktop --auto
mise run app:celular    # a mesma tela, em janela de celular
mise run test           # == ./gradlew :composeApp:desktopTest
mise run apk            # == ./gradlew :composeApp:assembleDebug
mise run apk:instalar   # instala no emulador e abre o deep link
mise run studio         # Android Studio na área de trabalho (só no Codespace)
```

> A task `app` olha a variável `CODESPACES` e, lá dentro, acrescenta `DISPLAY=:1`, que
> manda a janela para a área de trabalho do navegador. É o mesmo comando dos passos
> anteriores, com o detalhe do ambiente resolvido — quem grava a tela não precisa lembrar.

📖 [mise — tasks](https://mise.jdx.dev/tasks/)

---

## Onde isto encosta no MUSI

`@Composable`, estado elevado, componente próprio e `LazyColumn` são as mesmas peças da
pasta `app/` do MUSI — aqui isoladas, numa tela só.
