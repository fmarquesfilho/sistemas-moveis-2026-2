# Passos — tela de Tarefas em Compose

Uma tela construída **do zero, incrementalmente**, em Compose Multiplatform. Cada passo roda. Passos 1 a 5: aula de 14/09. Passos 6 a 10: segunda parte da Sprint 1, em vídeo (a partir do Passo 6 o código fica dividido em `Tarefa.kt`, `Telas.kt` e `App.kt`). **Esta pasta já é um projeto KMP completo** (alvos **Android** e **Desktop**); a UI fica em `composeApp/src/commonMain/kotlin/.../App.kt` e é a **mesma** nos dois alvos.

## Como abrir e rodar

O projeto usa AGP 9.1: abre no **Android Studio Panda 2 (2025.3.2) ou mais novo**. O Android Studio do laboratório (Ladybug, 2024) **não sincroniza** este projeto; lá, use o Codespaces e instale o APK no emulador (abaixo).

**No Codespaces** (nada instalado na máquina): crie um Codespace deste repositório (Code → Codespaces). O `.devcontainer/` já traz Java, o SDK Android e uma área de trabalho no navegador (aba **Portas** → **6080**, senha `vscode`).

- **`@Preview` no Android Studio:** no terminal, `bash .devcontainer/android-studio.sh`; na área de trabalho, abra `Telas.kt` e clique **Split**.
- **App com Hot Reload, em janela de celular:**

  ```bash
  cd exemplos/tarefas-compose
  ./gradlew :composeApp:hotDevDesktop --auto --className=br.ufrn.exemplo.tarefas.PreviewCelularKt --funName=TelaCelular
  ```

  Ao salvar um arquivo, a janela atualiza em segundos.

**No emulador ou no celular, via APK:** `./gradlew :composeApp:assembleDebug` gera `composeApp/build/outputs/apk/debug/composeApp-debug.apk`. Baixe (botão direito → Download) e arraste para a janela do emulador — funciona em qualquer versão do Android Studio — ou instale com `adb install -r composeApp-debug.apk`. A chave de debug fica em `keystore/`, então um APK de qualquer máquina atualiza o de outra. Se o Android acusar `signatures do not match`, desinstale uma vez: `adb uninstall br.ufrn.exemplo.tarefas`.

**No Android Studio local (Panda 2 ou mais novo):** abra a pasta `tarefas-compose/`, deixe o Gradle sincronizar, escolha **composeApp** e o emulador e clique **Run ▶**. Desktop: `./gradlew :composeApp:hotRunDesktop --auto`.

A UI compartilhada fica em `composeApp/src/commonMain/kotlin` — é lá que entram os passos abaixo. `App()` é montada pelo `MainActivity` (Android) e pelo `main()` (desktop).

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

> **Fundamento (fecha a Sprint 0).** `remember { mutableStateOf(...) }` guarda estado entre recomposições. Clicar muda o estado → o Compose **recompõe** o que depende dele.

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

> **Fundamento.** O componente **recebe** o que mostra (`tarefa`) e **devolve** o evento (`onAlternar`), sem guardar estado próprio. É o *state hoisting*.

---

## Passo 3 — lista com `LazyColumn`

```kotlin
LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
  items(tarefas, key = { it.id }) { tarefa ->
    CartaoTarefa(tarefa) { /* alterna 'feita' */ }
  }
}
```

> **Fundamento.** `LazyColumn` só cria os itens **visíveis** — desempenho em listas grandes. `key` estabiliza a rolagem quando a lista muda.

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

> **Fundamento.** A validação é derivada do estado (`valido`); a UI reage — o botão só habilita quando o título é válido.

---

## Passo 5 — Material 3 e tema

`MaterialTheme { ... }` já dá o esquema de cor, tipografia e componentes (Material 3) — mas, sem `colorScheme`, é **sempre claro**. Para seguir o sistema, e com uma `Surface` na raiz:

```kotlin
@Composable
fun App() {
  val cores = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
  MaterialTheme(colorScheme = cores) {
    Surface(Modifier.fillMaxSize()) {        // pinta o fundo e a cor do texto do tema
      Column(Modifier.safeDrawingPadding().padding(16.dp)) { /* ... */ }
    }
  }
}
```

> **Fundamento.** Componentes (`Button`, `Card`, `OutlinedTextField`) já seguem o tema. Cor e tipografia vêm do `MaterialTheme`, não são fixadas à mão. Sem a `Surface`, o modo escuro troca as cores dos componentes, mas o fundo e o texto solto continuam claros. `safeDrawingPadding()` afasta o conteúdo da barra de status: o `MainActivity` chama `enableEdgeToEdge()`, que desenha de ponta a ponta e ajusta a cor dos ícones da barra.

Teste no emulador: `adb shell cmd uimode night yes` (e `no` para voltar).

---

## `@Preview` (no Android Studio)

Em `commonMain`, anote uma função sem parâmetros para ver a tela sem rodar o app:

```kotlin
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AppPreview() { App() }
```

> O `@Preview` é desenhado pela IDE: aparece no Android Studio (local ou no Codespaces, pelo `android-studio.sh`), não no VS Code. O equivalente com Hot Reload é `@DevelopmentEntryPoint`, em `desktopMain/.../PreviewCelular.kt`.

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

> Antes, caixa e texto eram **dois** elementos para o leitor de tela, e a área de toque era só a caixa. Agora a linha é **um** elemento, com papel de caixa de seleção, o texto e o estado, tocável de ponta a ponta. `heading()` deixa pular de título em título.

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

> A lista fica **acima** do `NavHost` (estado elevado): as duas telas veem os mesmos dados. Deep link no Android: `adb shell am start -a android.intent.action.VIEW -d "tarefas://tarefa/2"` (o `AndroidManifest.xml` declara o esquema `tarefas`).

📖 [Navigation in Compose Multiplatform](https://kotlinlang.org/docs/multiplatform/compose-navigation.html)

---

## Passo 8 — Janela compacta × larga

```kotlin
val largo = currentWindowAdaptiveInfo().windowSizeClass
    .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

if (largo) Row { TelaLista(/* ... */); VerticalDivider(); TelaDetalhe(/* ... */) }
else NavHost(/* Passo 7 */)
```

> Celular em pé: uma tela por vez. Celular deitado, tablet ou janela desktop larga (600 dp ou mais): lista e detalhe lado a lado. Teste girando o emulador ou redimensionando a janela desktop.

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

> O teste de interface consulta a mesma árvore de semântica que o leitor de tela usa: se o Passo 6 for desfeito, `assertIsToggleable()` falha.

📖 [Testing Compose Multiplatform UI](https://kotlinlang.org/docs/multiplatform/compose-test.html)

---

## Passo 10 — o ambiente em tasks

Os comandos destes passos viram nomes curtos no [`mise.toml`](../../mise.toml) da raiz do repositório, que também fixa a versão do JDK:

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

> A task `app` olha a variável `CODESPACES` e, lá dentro, acrescenta `DISPLAY=:1`, que manda a janela para a área de trabalho do navegador. É o mesmo comando dos passos anteriores, com o detalhe do ambiente resolvido — quem grava a tela não precisa lembrar.

📖 [mise — tasks](https://mise.jdx.dev/tasks/)

---

## Passos 11 a 16 — Sprint 2: estado, dados reais e testes

A partir daqui, o app deixa de guardar tudo na tela e passa a buscar as tarefas na API de Tarefas de Web II (`web2-2026-2/exemplos/ktor-tarefas`), com o estado num ViewModel, as camadas separadas, o Koin ligando as peças, uma cópia local para abrir sem rede e testes de ViewModel com Turbine.

```
br.ufrn.exemplo.tarefas
├── App.kt, Modulos.kt           tema, navegação e a raiz de composição (Koin)
├── dominio/Tarefa.kt            Tarefa, regras, a porta RepositorioDeTarefas, falhas
├── dados/                       RepositorioRemoto (Ktor Client), RepositorioComCache
│                                (DataStore), RepositorioEmMemoria, Endereco (expect/actual)
└── apresentacao/                TarefasViewModel, EstadoTela, Telas.kt
```

Versões novas: Lifecycle ViewModel 2.9.6 (a mesma da Navigation 2.9.2), Koin 4.2.2, Ktor Client 3.5.2 (OkHttp no Android, CIO no desktop), DataStore 1.2.1, kotlinx-coroutines 1.11.0, Turbine 1.2.1.

### Passo 11 — O estado sai da tela: ViewModel com `StateFlow`

```kotlin
class TarefasViewModel : ViewModel() {
    private val _estado = MutableStateFlow(EstadoLista())     // só o ViewModel altera
    val estado: StateFlow<EstadoLista> = _estado.asStateFlow()  // a tela só lê

    fun adicionar() = _estado.update { ... }                  // eventos viram funções
}

// Na tela:
val estado by viewModel.estado.collectAsStateWithLifecycle()
```

O texto do formulário e a lista sobrevivem à rotação e à troca de destino. O alvo desktop precisa de `kotlinx-coroutines-swing` para ter `Dispatchers.Main`.

### Passo 12 — Carregar sem travar a tela: `suspend`, `viewModelScope` e estado selado

```kotlin
sealed interface EstadoTela {
    data object Carregando : EstadoTela
    data object Vazio : EstadoTela
    data class Sucesso(val tarefas: List<Tarefa>) : EstadoTela
    data class Erro(val mensagem: String) : EstadoTela
}

fun carregar() {
    _estado.update { it.copy(tela = EstadoTela.Carregando) }
    viewModelScope.launch {                          // cancelada quando o ViewModel some
        val tela = try { ... repositorio.listar() ... } catch (...) { EstadoTela.Erro(...) }
        _estado.update { it.copy(tela = tela) }
    }
}
```

Na tela, um `when (estado.tela)` sem `else`: um caso novo deixa de compilar até ser tratado. O repositório em memória tem uma demora de 800 ms, para ver o indicador de carregamento.

### Passo 13 — Camadas e injeção de dependência com Koin

O código se divide em `dominio`, `dados` e `apresentacao`. O domínio não importa Compose, Ktor nem DataStore. O ViewModel recebe o repositório pelo construtor, e o Koin monta tudo:

```kotlin
val moduloTarefas = module {
    single<RepositorioDeTarefas> { RepositorioEmMemoria() }
    viewModelOf(::TarefasViewModel)
}

KoinApplication(application = { modules(moduloTarefas) }) { ... }   // em App()
viewModel: TarefasViewModel = koinViewModel()                       // em Conteudo()
```

### Passo 14 — Dados reais: Ktor Client, DTO e erros de rede com nome

`RepositorioRemoto` faz `GET` e `POST /tarefas` na API de Web II. O JSON chega num DTO (`TarefaDto`), convertido para a `Tarefa` do domínio. As exceções do Ktor viram falhas do domínio, e cada uma tem a sua mensagem na tela:

| O que aconteceu | Exceção do Ktor | Falha do domínio |
|---|---|---|
| API fora do ar, sem rede | `IOException` | `SemConexao` |
| status 4xx ou 5xx | `ResponseException` (com `expectSuccess = true`) | `RespostaInvalida` |
| JSON fora do formato | `ContentConvertException`, `SerializationException` | `RespostaInvalida` |

Endereço da API: `http://localhost:8080` no desktop (ou `TAREFAS_API`) e `http://10.0.2.2:8080` no emulador (`expect`/`actual`). Sem a API, o app roda com o repositório em memória: `TAREFAS_API=memoria ./gradlew :composeApp:run` ou `mise run app:sem-api` (no Codespace, `TAREFAS_API=memoria` já vem definido). O Android precisa da permissão `INTERNET` e, para `http://` local, de `usesCleartextTraffic`.

```bash
# terminal 1, no repositório de Web II: a API, o banco e o serviço Go
cd web2-2026-2/exemplos && docker compose up --build
# terminal 2, aqui
./gradlew :composeApp:run
```

### Passo 15 — Abrir sem rede: a última lista guardada com DataStore

`RepositorioComCache` envolve o remoto: guarda cada lista recebida e, sem conexão, devolve a última guardada, com `doCache = true`. A tela mostra "Sem conexão: mostrando a última lista salva.". O diretório do arquivo vem de cada plataforma: `filesDir` no Android, `~/.tarefas-compose` no desktop (`App(diretorioDeDados = ...)`).

Para conferir: abra o app com a API no ar, feche, pare a API (`docker compose stop api-ktor`) e abra de novo.

### Passo 16 — Testes de ViewModel com Turbine e dublês

```kotlin
@Test
fun carregarPassaPorCarregandoEChegaASucesso() = runTest {
    Dispatchers.setMain(StandardTestDispatcher(testScheduler))   // relógio virtual
    TarefasViewModel(RepositorioFalso()).estado.test {           // Turbine
        assertEquals(EstadoTela.Carregando, awaitItem().tela)
        assertIs<EstadoTela.Sucesso>(awaitItem().tela)
    }
}
```

O `RepositorioFalso` espera 500 ms de tempo virtual: o teste vê o estado intermediário (`Carregando`) e não espera de verdade. São 7 testes de ViewModel, 7 de interface, 4 do repositório remoto (com `MockEngine`), 3 do cache, 3 de regras e 1 do módulo do Koin: 25 no total, todos no alvo desktop.

```bash
./gradlew :composeApp:desktopTest
```

📖 [Android — StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) · [Compose Multiplatform — ViewModel](https://kotlinlang.org/docs/multiplatform/compose-viewmodel.html) · [Koin — Compose Multiplatform](https://insert-koin.io/docs/reference/koin-compose/compose) · [Ktor — Client](https://ktor.io/docs/client-create-new-application.html) · [DataStore — Kotlin Multiplatform](https://developer.android.com/kotlin/multiplatform/datastore) · [Turbine](https://github.com/cashapp/turbine)

---

## Onde isto encosta no MUSI

`@Composable`, estado elevado, componente próprio e `LazyColumn` são as mesmas peças da pasta `app/` do MUSI — aqui isoladas, numa tela só.
