---
marp: true
theme: default
paginate: true
backgroundColor: #ffffff
color: #1a1a2e
style: |
  section {
    font-family: 'Calibri', sans-serif;
    padding: 36px 48px;
    font-size: 1.3em;
  }
  h1 {
    font-family: 'Consolas', monospace;
    color: #1a56db;
    font-size: 1.6em;
    margin-bottom: 0.3em;
    border-bottom: 2px solid #e5e7eb;
    padding-bottom: 0.2em;
  }
  h2 {
    font-family: 'Consolas', monospace;
    color: #374151;
    font-size: 1.2em;
    margin-bottom: 0.25em;
  }
  h3 { color: #6b7280; font-size: 0.9em; margin: 0.2em 0; }
  strong { color: #b45309; }
  em { color: #6b7280; }
  code {
    font-family: 'Consolas', monospace;
    background: #e5e7eb;
    color: #1e3a5f;
    padding: 0.08em 0.3em;
    border-radius: 3px;
    font-size: 1.00em;
  }
  pre {
    background: #f3f4f6 !important;
    border: 1px solid #d1d5db;
    border-left: 3px solid #1a56db;
    border-radius: 6px;
    padding: 0.7em 1em;
    margin: 0.4em 0;
  }
  pre code {
    background: transparent;
    color: #1e3a5f;
    font-size: 0.85em;
    padding: 0;
    line-height: 1.5;
  }
  table { font-size: 0.95em; width: 100%; border-collapse: collapse; }
  th {
    background: #e5e7eb;
    color: #1a56db;
    font-family: 'Consolas', monospace;
    padding: 0.35em 0.7em;
    border: 1px solid #d1d5db;
  }
  td { background: #ffffff; padding: 0.28em 0.7em; border: 1px solid #d1d5db; color: #1a1a2e; }
  tr:nth-child(even) td { background: #f9fafb; }
  ul { margin: 0.25em 0; padding-left: 1.3em; }
  li { margin: 0.18em 0; font-size: 0.88em; line-height: 1.4; }
  blockquote {
    border-left: 3px solid #1a56db;
    background: #eff6ff;
    padding: 0.4em 0.9em;
    margin: 0.5em 0;
    font-style: normal;
    color: #1e3a5f;
    border-radius: 0 5px 5px 0;
    font-size: 1.00em;
  }
  .columns { display: flex; gap: 1.8em; }
  .col { flex: 1; }
  .pill-red   { display:inline-block; background:#fee2e2; border:1.5px solid #dc2626; color:#dc2626; font-family:'Consolas',monospace; font-weight:bold; font-size:0.85em; padding:0.12em 0.6em; border-radius:20px; }
  .pill-green { display:inline-block; background:#dcfce7; border:1.5px solid #16a34a; color:#16a34a; font-family:'Consolas',monospace; font-weight:bold; font-size:0.85em; padding:0.12em 0.6em; border-radius:20px; }
  .pill-blue  { display:inline-block; background:#dbeafe; border:1.5px solid #1a56db; color:#1a56db; font-family:'Consolas',monospace; font-weight:bold; font-size:0.85em; padding:0.12em 0.6em; border-radius:20px; }
  section.lead { justify-content: center; }
  section.lead h1 { font-size: 2.4em; border-bottom: none; }
  section.lead h2 { font-size: 1.5em; color: #6b7280; }
  .tag { display:inline-block; background:#f3f4f6; border:1px solid #d1d5db; color:#374151; font-size:0.85em; padding:0.1em 0.5em; border-radius:4px; font-family:'Consolas',monospace; }


---

# Desenvolvimento de Sistemas para Dispositivos Móveis

## Responsividade, acessibilidade, navegação e testes

DIM0524 — Turma 01 · Sprint 1 · 21/09

Prof. Fernando · UFRN · 2026.2

---

# Roteiro da semana

**Segunda, 21/09**

| Bloco | O que vemos |
|---|---|
| Ambiente | Codespaces: `@Preview` e Hot Reload no navegador |
| Responsividade | Classes de tamanho de janela, lista e detalhe |
| Acessibilidade | Árvore de semântica, alvos de toque, leitor de tela |
| Navegação | Rotas tipadas, argumentos, pilha de retorno, deep link |
| Testes | `kotlin.test` e teste de interface · oficina |

**Quarta, 23/09 — Acompanhamento online** (projeto)
**28 e 30/09 — Apresentações** · 🚀 **Entrega da Sprint 1: 02/10, 23:59**

---

# Ambiente: o laboratório e o Codespaces

O Android Studio do laboratório (Ladybug, 2024) **não abre** o exemplo: o AGP 9.1 exige o Panda 2 (2025.3.2) ou mais novo.

| Precisa de | Onde |
|---|---|
| `@Preview` | Android Studio **dentro do Codespace**: `bash .devcontainer/android-studio.sh` |
| App com Hot Reload | Janela de celular no Codespace: `hotDevDesktop` |
| Android de verdade | APK gerado no Codespace, instalado no **emulador do laboratório** |

Tudo aparece na **porta 6080** (área de trabalho no navegador, senha `vscode`).

> O `@Preview` é desenhado pela IDE; o VS Code não tem esse renderizador.

---

# O APK no emulador do laboratório

```bash
./gradlew :composeApp:assembleDebug
# composeApp/build/outputs/apk/debug/composeApp-debug.apk
```

- Baixe pelo explorador do VS Code e **arraste para o emulador**
- Funciona em qualquer versão do Android Studio (imagem Android 7 ou mais nova)
- A **chave de debug está no repositório** (`keystore/`): um APK de qualquer máquina atualiza o de outra

Já tinha o app de outra máquina? `INSTALL_FAILED_UPDATE_INCOMPATIBLE` → desinstale uma vez:

```bash
adb uninstall br.ufrn.exemplo.tarefas
```

---

# Onde paramos

Em 14/09, uma tela de tarefas, tudo em `App.kt`:

```
  LazyColumn com key · formulário com validação
  estado elevado · val + copy + reatribuição
  Material 3, claro e escuro
```

Hoje: **duas telas**, e o código se divide.

| Arquivo | O que tem |
|---|---|
| `Tarefa.kt` | modelo e **regras puras** (`tituloValido`, `alternando`) |
| `Telas.kt` | `TelaLista`, `TelaDetalhe`, componentes, previews |
| `App.kt` | rotas, estado, uma tela ou duas (`Conteudo`) |

---

<!-- _class: lead -->

# Responsividade

## A largura da janela decide

---

# Classes de tamanho de janela

| Classe | Largura | Exemplos |
|---|---|---|
| Compacta | < 600 dp | celular em pé |
| Média | 600 a 839 dp | celular deitado, tablet pequeno |
| Expandida | ≥ 840 dp | tablet deitado, desktop |

```kotlin
val largo = currentWindowAdaptiveInfo().windowSizeClass
    .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
```

- **Janela**, não aparelho: tela dividida, dobrável, janela desktop
- **Largura**, não orientação: tablet em pé é mais largo que celular deitado
- Conferido: 599 px → uma tela; 600 → dois painéis

---

# Lista e detalhe

<div class="columns">
<div class="col">

**Compacta** <span class="pill-blue">navega</span>

```
┌────────────┐     ┌────────────┐
│ Lista      │ ──▶ │ Detalhe    │
│            │ ◀── │  Voltar    │
└────────────┘     └────────────┘
```

</div>
<div class="col">

**Larga** <span class="pill-green">seleciona</span>

```
┌────────────┬───────────────┐
│ Lista      │ Detalhe       │
│            │ (selecionada) │
└────────────┴───────────────┘
```

</div>
</div>

- As **mesmas telas** nos dois modos; muda quem as organiza
- `App()` decide, `Conteudo(largo)` monta: o teste escolhe o modo
- Na raiz: `Surface` (fundo do tema) e `safeDrawingPadding()` (barra de status)

---

<!-- _class: lead -->

# Acessibilidade

## O leitor de tela lê a árvore de semântica

---

# Antes e depois

<div class="columns">
<div class="col">

**14/09** <span class="pill-red">2 nós</span>

```
Node #3  Role = 'Checkbox'
         ToggleableState = 'Off'
Node #4  Text = '[Estudar Compose]'
```

Caixa sem nome, texto sem ação

</div>
<div class="col">

**21/09** <span class="pill-green">1 nó</span>

```
Node #9  Role = 'Checkbox'
         Text = '[Estudar Compose]'
         ToggleableState = 'Off'
         MergeDescendants = 'true'
```

A linha inteira: nome, papel, estado

</div>
</div>

```kotlin
Row(Modifier.toggleable(value = tarefa.feita, role = Role.Checkbox,
                        onValueChange = { onAlternar() })) {
    Checkbox(checked = tarefa.feita, onCheckedChange = null) // o clique é da linha
    Text(tarefa.titulo)
}
```

---

# O que a rubrica pede

| Critério | Como |
|---|---|
| Descrições de conteúdo | `contentDescription` no que não tem texto; `null` no decorativo |
| Contraste verificado | cores pelos papéis do tema; Accessibility Scanner |
| Alvos ≥ 48 dp | componentes Material 3 já garantem; cuidado com `clickable` à mão |
| Leitor de tela testado | TalkBack: deslizar para navegar, toque duplo para ativar |

Mais: títulos com `semantics { heading() }`, texto em `sp`.

> Descrição diz **o que faz** ("Apagar tarefa"), não **como é** ("ícone de lixeira").

---

<!-- _class: lead -->

# Navegação

## Um grafo, com pilha de retorno

---

# Rotas tipadas

```kotlin
@Serializable object Lista
@Serializable data class Detalhe(val id: Int)

NavHost(navController, startDestination = Lista) {
    composable<Lista> {
        TelaLista(/* ... */ onAbrir = { id -> navController.navigate(Detalhe(id)) })
    }
    composable<Detalhe> { entrada ->
        val rota = entrada.toRoute<Detalhe>()
        TelaDetalhe(tarefas.find { it.id == rota.id }, /* ... */
                    onVoltar = { navController.popBackStack() })
    }
}
```

- Argumento é **propriedade**, conferida pelo compilador
- Telas recebem **lambdas**, não o `NavController`
- Passe o **id**, não o objeto; o estado mora **acima** do `NavHost`

---

# Pilha de retorno e deep link

- `navigate` empilha; `popBackStack` desempilha; o **Voltar do sistema** também (conferido)
- No desktop não há Voltar do sistema: botão na tela

```kotlin
composable<Detalhe>(
    deepLinks = listOf(navDeepLink<Detalhe>(basePath = "tarefas://tarefa")),
)
```

```xml
<intent-filter>
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <data android:scheme="tarefas" android:host="tarefa" />
</intent-filter>
```

```bash
adb shell am start -a android.intent.action.VIEW -d "tarefas://tarefa/2"
```

> Aberto por deep link, o Voltar leva para a lista, não para fora do app.

---

<!-- _class: lead -->

# Testes

## `kotlin.test` e teste de interface

---

# Duas camadas

<div class="columns">
<div class="col">

**Regras puras** <span class="pill-green">kotlin.test</span>

```kotlin
@Test
fun tituloEmBrancoNaoEValido() {
    assertFalse(tituloValido("   "))
}
```

3 testes em 0,018 s

</div>
<div class="col">

**Interface** <span class="pill-blue">runComposeUiTest</span>

```kotlin
@Test
fun linhaInteiraEUmaCaixaDeSelecao() =
  runComposeUiTest {
    setContent { Conteudo(largo = false) }
    onNodeWithText("Estudar Compose")
      .assertIsToggleable().assertIsOff()
    onNodeWithText("Estudar Compose")
      .performClick()
    onNodeWithText("Estudar Compose")
      .assertIsOn()
  }
```

</div>
</div>

`./gradlew :composeApp:desktopTest` — sem emulador. O teste usa a **mesma árvore** do leitor de tela.

---

# No CI

O workflow da Sprint 0 (build, `ktlint`, `detekt`) ganha os testes:

```yaml
- run: sudo apt-get update && sudo apt-get install -y libgl1 libegl1 libfontconfig1
- run: ./gradlew :composeApp:desktopTest
```

- Os testes de interface **não abrem janela**, mas o Skia precisa de bibliotecas gráficas
- Sem elas (conferido num container Linux mínimo): `UnsatisfiedLinkError: ... libGL.so.1`
- Com elas: os 8 testes passam, sem tela

> Dois nós com o mesmo texto? `onNodeWithText` falha com *Expected exactly '1' node but found '2'*. Use `onAllNodesWithText(...)[0]`.

---

# Oficina: testes para o projeto

1. Regras em **funções puras**, cobertas com `kotlin.test`
2. **Uma tela, um teste**: monta com dados fixos, confere o que aparece
3. **Formulário**: um caso inválido, um válido
4. **Navegação**: abre, age, volta, confere
5. **Acessibilidade**: `assertIsToggleable`, `onNodeWithContentDescription`

> Tela testável = tela que recebe estado e devolve eventos. É o estado elevado de 14/09, agora com prova.

---

# Entrega da Sprint 1 — 02/10, 23:59

| Critério | Peso |
|---|---|
| Telas do MVP, com componentes próprios | 25% |
| Navigation Compose: rotas tipadas, argumentos, ≥ 1 deep link demonstrado | 25% |
| Tema claro/escuro, layout em ≥ 2 larguras, sem overflow | 20% |
| Acessibilidade: descrições, contraste, alvos ≥ 48 dp, leitor de tela | 15% |
| ≥ 5 testes de interface, verdes no CI | 15% |

Guia e tarefas: `docs/SPRINT-1.md` e `docs/SPRINT-1-TAREFAS.md`. Apresentações: 28/09 (Coorte B, online) e 30/09 (Coorte A, em sala).

---

# Próximas aulas

- **23/09** — acompanhamento online: tragam a navegação e os primeiros testes
- **28 e 30/09** — apresentações da Sprint 1
- **05/10** — Sprint 2: coroutines, `Flow` e `StateFlow`, ViewModel multiplataforma
- **07/10** (online) — estado de tela com classes seladas, camadas, Koin, testes com Turbine

---

# Onde estudar depois

| Fonte | Foco |
|---|---|
| `leituras/moveis-s1-pte2.md` | Esta aula, com erros comuns e exercícios |
| `exemplos/tarefas-compose/PASSOS.md` | Passos 6 a 9 |
| `developer.android.com/develop/ui/compose/accessibility` | Acessibilidade em Compose |
| `kotlinlang.org/docs/multiplatform/compose-navigation.html` | Navegação multiplataforma |
| `kotlinlang.org/docs/multiplatform/compose-test.html` | Testes de interface |
