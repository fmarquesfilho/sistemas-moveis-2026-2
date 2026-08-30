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


# Compose, componentes e a CI

## DIM0524 — Sistemas para Dispositivos Móveis

### Aulas 03–04 (Sprint 0) · 31/08 e 02/09

<!-- _footer: 'Prof. Fernando · UFRN/DIMAp · 2026.2' -->

---

# Roteiro da semana

**Segunda, 31/08 — Compose**

| Parte | O quê |
|---|---|
| Onde o app se encaixa | O app, a API e o domínio compartilhado |
| A estrutura do projeto | Os módulos `shared` e `app`, e a regra de dependência |
| UI declarativa | O que é `@Composable` e recomposição |
| Estado e layout | `remember`, elevação de estado, `Column`/`Row`/`Box`, preview |

**Quarta, 02/09 — Componentes, tema e CI**

| Parte | O quê |
|---|---|
| Componentes próprios | Reutilizáveis, com *slots* |
| Material 3 | Esquema de cor e tipografia |
| Kotlin idiomático | Funções de extensão e de escopo |
| Projeto e CI | Catálogo de versões, `kdoctor`, `ktlint` e `detekt` |

---

# Onde paramos

Na aula passada, a linguagem e a plataforma:

```
  Kotlin       val/var, null safety, data class, sealed, when
  KMP          commonMain, expect/actual, um módulo, vários alvos
  Compose      a mesma API para Android, desktop e iOS
```

Hoje usamos isso para construir interface. O domínio — `Obra`, `Faceta`, `Filtro` — já está em `commonMain`; falta a tela que o mostra.

---

# Onde o app se encaixa

O app não vive sozinho: ele é o cliente de um sistema com outras partes.

```
   app (Compose)  ──►  API (Ktor/Quarkus)  ──►  serviço de busca  ──►  dados
   a tela              as regras                  a consulta
        │
        └── usa o domínio compartilhado (shared/): Obra, Faceta, Filtro
```

- Na Sprint 0 vocês escolhem a estratégia de backend (ver `STACK.md`); as telas vêm primeiro
- O mesmo domínio vive no app e na API, o `shared/` é importado pelos dois

---

# A estrutura do projeto

Os dois módulos — e a regra que os separa:

```
  shared/     o domínio, Kotlin puro    → commonMain, sem Compose
     │  build.gradle.kts: kotlin { jvm(); /* androidTarget() */ }
     ▼
  app/        a interface, em Compose    → depende de :shared
        build.gradle.kts: implementation(project(":shared"))
```

No MUSI, `shared/commonMain` não pode importar Compose, e o compilador garante isso. O alvo `androidTarget()` fica comentado até a sprint onde vamos fazer o deploy para Android ou iOS.

> Observem que a UI conhece o domínio; o domínio não conhece a UI. Esse padrão de projeto permite que as regras de negócio sejam testadas, evoluídas e reaproveitadas sem depender da interface: o mesmo shared/ roda no app (Compose) e no serviço api-ktor, e a tela pode mudar sem mexer no domínio.

---

# Onde estudar depois

| Guia | Foco |
|---|---|
| Compose Multiplatform · `kotlinlang.org/compose-multiplatform` | A API que usamos |
| Now in Android — Compose · `developer.android.com/compose` | Referência e receitas |
| Compose Hot Reload · `github.com/JetBrains/compose-hot-reload` | Hot reload no desktop |

O material do Android serve: o Compose Multiplatform é a mesma API do Jetpack Compose.

---

<!-- _class: lead -->

# Parte 1

## UI declarativa

---

# Duas formas de construir uma tela

| Imperativa (Views clássicas) | Declarativa (Compose) |
|---|---|
| Cria o widget e guarda a referência | Descreve a tela em função do estado |
| `textView.setText(novo)` a cada mudança | Muda o estado; a UI se redesenha |
| A tela e os dados podem divergir | A tela é sempre reflexo do estado |

```
  Imperativa   estado muda → você atualiza cada widget na mão
  Declarativa  estado muda → a função roda de novo e redesenha
```

> O bug clássico da forma imperativa é a tela mostrar um valor e os dados terem outro. Na declarativa isso não acontece: a tela é uma função do estado.

---

# `@Composable`: uma função que descreve UI

```kotlin
@Composable
fun Saudacao(nome: String) {
    Text("Olá, $nome")
}
```

- Uma função anotada com `@Composable` descreve um pedaço de tela
- Não devolve um objeto de view: emite elementos ao ser chamada
- Só pode ser chamada de dentro de outra função `@Composable`

```kotlin
@Composable
fun Tela() {
    Column {
        Saudacao("Ana")
        Saudacao("Bruno")
    }
}
```

> Telas são feitas compondo funções pequenas, como `CartaoObra` dentro de uma lista.

---

# Imperativa x Declarativa

A diferença que separa o Compose das Views imperativas clássicas está no que a função **faz** ao ser chamada.

```kotlin
// View clássica: fabrica e devolve um objeto
fun criarSaudacao(nome: String): TextView {
    val tv = TextView(contexto)
    tv.text = "Olá, $nome"
    return tv                      // ← você recebe o widget e o guarda
}

// Compose: não devolve nada; emite ao ser chamada
@Composable
fun Saudacao(nome: String) {
    Text("Olá, $nome")             // ← encaixa um nó Text na posição atual
}
```

- A função `@Composable` retorna `Unit`: não há objeto de view para guardar
- **Emitir** é registrar um nó na árvore de UI que o Compose está montando

> Chamar `Saudacao("Ana")` não te dá um `Text` — coloca um `Text` ali. A tela é efeito da chamada, não o valor de retorno.

---

# Aninhar é montar a árvore

Como cada chamada emite na posição atual, o aninhamento das chamadas vira a estrutura da tela:

```kotlin
@Composable
fun Tela() {
    Column {
        Saudacao("Ana")
        Saudacao("Bruno")
    }
}
```

```
  Column
  ├── Text  "Olá, Ana"
  └── Text  "Olá, Bruno"
```

Quem sabe "onde estou montando" é um parâmetro invisível — o `Composer` — que o compilador injeta em toda função `@Composable`.

> Por isso **uma `@Composable` só pode ser chamada de dentro de outra**: ela precisa do `Composer` em escopo para saber onde encaixar o elemento.

---

# Imperativa x Declarativa

Como você nunca recebe o nó, não dá para atualizar a tela "por fora":

```kotlin
val t = Text("Olá")     // não há retorno: Text devolve Unit
t.setText("Oi")         // não há objeto nem setter para chamar
```

Para mudar o que aparece, muda-se o **estado** que a função lê — e o Compose chama a função de novo (recompõe).

| Views clássicas | Compose |
|---|---|
| Guarda a referência do widget | Não há referência para guardar |
| `widget.setX(novo)` atualiza | Muda o estado; a função reemite |
| Tela e dados podem divergir | Tela é sempre função do estado |

> No Compose você não conserta a tela, você a **descreve de novo**.

---

# Recomposição

Quando o estado que uma função lê muda, o Compose chama a função de novo — só ela, não a tela inteira. Isso é a recomposição.

```
  estado: filtro = "baião"          →  a lista recompõe, mostra 2 obras
  usuário seleciona "ijexá"         →  filtro = "ijexá"  →  recompõe, mostra 1
```

| Consequência | O que significa para você |
|---|---|
| A função pode rodar muitas vezes | Não adicione efeitos colaterais |
| A ordem de execução não é garantida | Não dependa de "rodou antes/depois" |
| Deve ser rápida e sem I/O | Ler arquivo ou rede aqui trava a tela |

> Pense na função como uma descrição, não como um passo a passo. O Compose decide quando executá-la.

---

<!-- _class: lead -->

# Parte 2

## Estado

O que faz a tela mudar.

---

# O problema: variável comum não sobrevive

```kotlin
@Composable
fun Contador() {
    var n = 0                       // ← recriada a cada recomposição
    Button(onClick = { n++ }) {
        Text("Cliques: $n")
    }
}
```

Isso não funciona: a cada recomposição `n` volta a zero, e o Compose nem sabe que precisa recompor.

Faltam duas coisas:

1. Lembrar o valor entre recomposições
2. Avisar o Compose quando ele muda

---

# `remember` + `mutableStateOf`

```kotlin
@Composable
fun Contador() {
    var n by remember { mutableStateOf(0) }
    Button(onClick = { n++ }) {
        Text("Cliques: $n")
    }
}
```

| Peça | Papel |
|---|---|
| `mutableStateOf(0)` | Um estado observável: mudou, recompõe quem o lê |
| `remember { ... }` | Preserva o valor entre recomposições |
| `by` | Para ler/escrever `n` direto, sem `.value` |

> `remember` sem `mutableStateOf` guarda mas não avisa; `mutableStateOf` sem `remember` avisa mas esquece. Os dois juntos são o padrão.

---

# `remember` com chave

`remember(chave)` recalcula quando a chave muda. É como derivar um valor sem recomputar à toa:

```kotlin
val visiveis = remember(obras, filtro) {
    if (filtro == null) obras
    else obras.filter { it.satisfaz(filtro) }
}
```

Enquanto `obras` e `filtro` não mudarem, a lista filtrada é reaproveitada. Quando um deles muda, o bloco roda de novo.

> É deste `TelaAcervo` do projeto de exemplo. Repare que a filtragem é derivada do estado, não um estado novo guardado à parte — menos coisa para manter em sincronia.

---

<!-- _class: lead -->

# Parte 3

## Elevação de estado

O padrão que a rubrica cobra.

---

# Guardar estado dentro nem sempre serve

```kotlin
@Composable
fun TelaAcervo(obras: List<Obra>) {
    var filtro by remember { mutableStateOf<Filtro?>(null) }   // preso aqui
    // ...
}
```

Funciona — mas ninguém de fora consegue decidir o que a tela mostra, testar com um filtro fixo, ou pré-visualizar estados diferentes.

O estado ficou preso dentro do componente.

---

# Elevar o estado (*state hoisting*)

Suba o estado para quem chama, e receba valor mais evento:

```kotlin
@Composable
fun TelaAcervo(
    obras: List<Obra>,
    filtro: Filtro?,                     // o que mostrar (valor)
    aoTrocarFiltro: (Filtro?) -> Unit,   // o que fazer ao mudar (evento)
    modifier: Modifier = Modifier,
) { /* ... */ }
```

```
  estado desce  ─────►  filtro
  evento sobe   ◄─────  aoTrocarFiltro(novo)
```

| Ganho | Por quê |
|---|---|
| Testável | Passe um filtro e verifique a saída |
| Pré-visualizável | Cada estado vira um `@Preview` |
| Reutilizável | O componente não decide nada sozinho |

> Estado desce, evento sobe. Um componente sem estado interno é o que a rubrica chama de próprio e reutilizável.

---

# Componente próprio: `CartaoObra`

Não conhece a tela em que está, não guarda estado, não decide nada sobre o acervo. Recebe uma obra e a desenha:

```kotlin
@Composable
fun CartaoObra(obra: Obra, modifier: Modifier = Modifier) {
    Card(modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(obra.titulo, style = MaterialTheme.typography.titleMedium)
            Text("${obra.artista} · ${obra.ano}",
                 style = MaterialTheme.typography.bodyMedium)
        }
    }
}
```

Serve na lista, numa tela de detalhe ou num preview. É o entregável da Sprint 0.

> Convenção: todo componente aceita `modifier: Modifier = Modifier` como último parâmetro. Já vemos por quê.

---

<!-- _class: lead -->

# Parte 4

## Layout e modificadores

---

# Os três contêineres

```
  Column            Row               Box
  ┌─────────┐       ┌───┬───┬───┐     ┌─────────┐
  │    A    │       │ A │ B │ C │     │  A  (B) │  ← empilha em Z
  │    B    │       └───┴───┴───┘     │         │
  │    C    │                         └─────────┘
  └─────────┘
  vertical          horizontal        sobrepõe
```

```kotlin
Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    Text("Acervo")
    Text("5 obras")
}
```

| Contêiner | Empilha | Alinha com |
|---|---|---|
| `Column` | de cima para baixo | `horizontalAlignment`, `verticalArrangement` |
| `Row` | da esquerda para a direita | `verticalAlignment`, `horizontalArrangement` |
| `Box` | em profundidade | `contentAlignment` |

---

# `Modifier`: tamanho, espaço, aparência, gesto

Um `Modifier` ajusta como um componente aparece e se comporta. A ordem importa — ela é aplicada de cima para baixo:

```kotlin
Text(
    "Acervo",
    modifier = Modifier
        .fillMaxWidth()      // ocupa a largura
        .padding(16.dp)      // espaço interno
)
```

| Modificador | Efeito |
|---|---|
| `.fillMaxWidth()` · `.fillMaxSize()` | Ocupa o espaço disponível |
| `.padding(16.dp)` | Espaço ao redor |
| `.size(48.dp)` | Tamanho fixo |
| `.clickable { ... }` | Reage ao toque |

> `.padding(8.dp).background(azul)` é diferente de `.background(azul).padding(8.dp)`. Trocar a ordem troca o resultado. Testem os dois no Hot Reload.

---

# Por que o `modifier` vem de fora

O componente não decide seu próprio espaçamento externo. Quem o usa decide:

```kotlin
@Composable
fun CartaoObra(obra: Obra, modifier: Modifier = Modifier) {
    Card(modifier.fillMaxWidth()) { /* ... */ }
}

// quem chama posiciona:
CartaoObra(obra, Modifier.padding(8.dp))
```

É a mesma ideia da elevação de estado, aplicada ao layout: o componente descreve o que é; onde ele fica é decisão de quem o coloca na tela.

> Um componente que fixa a própria margem externa não serve em dois lugares diferentes. Deixe o `modifier` entrar por parâmetro.

---

<!-- _class: lead -->

# Parte 5

## Preview e Hot Reload

---

# `@Preview`: ver sem rodar o app

Porque o componente tem estado elevado, cada estado vira uma pré-visualização:

```kotlin
@Preview
@Composable
fun CartaoObraPreview() {
    CartaoObra(
        Obra("obra-01", "Ponteio", "Edu Lobo", 1967, emptyList())
    )
}

@Preview
@Composable
fun ListaVaziaPreview() {
    TelaAcervo(obras = emptyList(), filtro = null, aoTrocarFiltro = {})
}
```

> Aqui aparece o retorno do estado elevado: dá para pré-visualizar a lista vazia e a lista cheia sem tocar no app. Componente com estado preso não permite isso.

---

# Hot Reload: o ciclo rápido

No alvo desktop, o hot reload aplica a mudança do código na hora, mantendo o estado da tela:

```
  edita o CartaoObra  →  salva  →  a tela atualiza em ~1 s
                                    sem reiniciar, sem emulador
```

| Ciclo | Custo |
|---|---|
| Emulador Android | dezenas de segundos por mudança |
| Desktop + Hot Reload | cerca de 1 segundo |

> No Codespaces: abra a porta 6080 (noVNC) e rode `DISPLAY=:1 ./gradlew :app:hotRunJvm -Pheadless` para recarregar ao salvar — ou `:app:run -Pheadless` só para ver a tela. Passo a passo em docs/COMO-RODAR.md.

---

<!-- _class: lead -->

# Parte 6

## Mãos à obra

---

# Oficina — 25 minutos <span class="pill-blue">alvo desktop</span>

Partindo do esqueleto KMP, com o `acervoDeExemplo` já pronto:

```
  1. Escreva CartaoObra(obra): título, artista · ano
  2. Monte TelaAcervo(obras) com uma LazyColumn de CartaoObra
  3. Eleve o filtro: receba `filtro` e `aoTrocarFiltro` por parâmetro
  4. Adicione FiltrosRapidos com FilterChip (todos / baião / até 1970)
  5. Crie dois @Preview: lista cheia e lista vazia
```

```kotlin
LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    items(visiveis, key = { it.id }) { obra -> CartaoObra(obra) }
}
```

> `LazyColumn` só compõe o que aparece na tela — é a lista que aguenta mil itens. `key` ajuda o Compose a reaproveitar os cartões ao rolar.

---

# Erros que aparecem sempre

| Erro | O que acontece |
|---|---|
| `var n = 0` sem `remember` | Volta a zero a cada recomposição |
| `remember` sem `mutableStateOf` | Guarda, mas a tela não recompõe |
| I/O ou log no corpo do `@Composable` | Roda muitas vezes, trava a tela |
| Estado preso dentro do componente | Não dá para testar nem pré-visualizar |
| `Column` gigante em vez de `LazyColumn` | Compõe tudo de uma vez; trava com lista grande |
| Cor crua em vez do tema | Ignora modo claro e escuro |

> Os dois primeiros respondem por quase toda dúvida de "por que não atualiza / por que zera". Guardem a dupla `remember { mutableStateOf(...) }`.

---

# Fecho de segunda

Vocês têm a primeira tela: um componente próprio, com estado elevado, rodando no desktop.

Quarta ela vira reutilizável de verdade (com slots e tema), o código fica idiomático, e o CI passa a exigir estilo com ktlint e detekt.

> Entre hoje e quarta: deixem `CartaoObra` e `TelaAcervo` compilando no desktop, com ao menos dois `@Preview`. Quem ainda não instalou o ambiente: rodem o `kdoctor`.

---

<!-- _class: lead -->

# Quarta · 02/09

## Componentes, tema e CI

Do "funciona" ao "reutilizável e verificado".

---

# Componente próprio, de novo

Na segunda, `CartaoObra` já era um componente próprio: recebe uma obra, não guarda estado, serve na lista e no preview.

```kotlin
@Composable
fun CartaoObra(obra: Obra, modifier: Modifier = Modifier) { … }
```

Hoje damos o próximo passo: componentes que recebem conteúdo de fora — os *slots*.

> Abram `app/src/commonMain/kotlin/br/ufrn/musi/ui/TelaAcervo.kt`: `CartaoObra` e `FiltrosRapidos` são os dois componentes próprios que a Sprint 0 pede.

---

# Slots: o conteúdo vem de fora

Um *slot* é um parâmetro `@Composable`. O componente decide o arranjo; quem chama decide o conteúdo.

```kotlin
@Composable
fun Secao(titulo: String, conteudo: @Composable () -> Unit) {
    Column(Modifier.padding(12.dp)) {
        Text(titulo, style = MaterialTheme.typography.titleMedium)
        conteudo()                         // ← o slot
    }
}

Secao("Acervo") {
    LazyColumn { items(obras) { CartaoObra(it) } }
}
```

> O `Card { … }` que vocês usaram na segunda já é assim: ele desenha a moldura e recebe o conteúdo por um slot. É o padrão mais comum do Compose.

---

# Material 3: cor e tipografia do tema

Nunca escreva cor ou tamanho cru. Use os papéis do tema:

```kotlin
Text(obra.titulo,  style = MaterialTheme.typography.titleMedium)
Text(obra.artista, color = MaterialTheme.colorScheme.onSurfaceVariant)
```

| Em vez de | Use |
|---|---|
| `fontSize = 20.sp` | `typography.titleMedium` |
| `Color(0xFF666666)` | `colorScheme.onSurfaceVariant` |

> É o que `CartaoObra` faz no MUSI. Usar papéis do tema é o que faz o app respeitar modo claro e escuro sem retrabalho.

---

# O tema num lugar só

`MaterialTheme` embrulha a árvore e injeta cor, tipografia e formas. Trocar o esquema muda o app inteiro:

```kotlin
MaterialTheme(colorScheme = meuEsquema) {
    TelaAcervo(obras = acervoDeExemplo, filtro = filtro, aoTrocarFiltro = { … })
}
```

> Definam o esquema uma vez, na raiz. Componente que fixa a própria cor quebra o modo escuro e não dá para reaproveitar noutro tema.

---

# Kotlin idiomático: funções de escopo

Cinco funções curtas que deixam o código de UI mais limpo:

| Função | Devolve | Uso típico |
|---|---|---|
| `let` | o resultado do bloco | agir sobre algo não-nulo |
| `apply` | o próprio objeto | configurar um objeto |
| `also` | o próprio objeto | efeito colateral (log) |
| `run` / `with` | o resultado do bloco | agrupar chamadas |

```kotlin
val texto = filtro?.let { descrever(it) } ?: "sem filtro"
```

> Esse `let` é do `TelaAcervo` do MUSI: só chama `descrever` quando `filtro` não é nulo, e cai no `?:` quando é. Sem `if` e sem variável temporária.

---

# Gradle e o catálogo de versões

Uma fonte da verdade para todas as versões: `gradle/libs.versions.toml`.

```toml
[versions]
kotlin = "2.4.10"
compose = "1.12.0"

[libraries]
koin-core = { module = "io.insert-koin:koin-core", version.ref = "koin" }

[plugins]
compose-multiplatform = { id = "org.jetbrains.compose", version.ref = "compose" }
```

```kotlin
// nos build.gradle.kts:
plugins { alias(libs.plugins.compose.multiplatform) }
dependencies { implementation(libs.koin.core) }
```

> Versão duplicada em vários módulos é fonte garantida de conflito. O catálogo mantém tudo num arquivo só — é o `libs.versions.toml` do MUSI.

---

# `kdoctor`: antes de pedir socorro

O ambiente KMP tem muitas peças (JDK, Android SDK, Xcode no Mac). O `kdoctor` diagnostica o que falta:

```bash
kdoctor
  [✓] Operating System
  [✓] Java
  [✗] Android Studio — plugin do KMP ausente
```

> Rodem o `kdoctor` antes de abrir uma dúvida de ambiente. Ele resolve a maior parte, e economiza a aula.

---

# CI: agora com `ktlint` e `detekt`

O workflow deixa de só compilar e passa a exigir estilo e qualidade:

```
  push / PR ──► compila ──► ktlintCheck ──► detekt ──► verde
```

| Ferramenta | Pega |
|---|---|
| ktlint | Formatação fora do padrão Kotlin |
| detekt | Complexidade, *code smells*, funções longas |

No MUSI, o job `kotlin` do `.github/workflows/ci.yml` já roda os testes; ktlint e detekt entram a partir da Sprint 1. Para vocês, valem já na Sprint 0.

> Estilo deixa de ser opinião em revisão: a ferramenta decide, e o CI recusa o que estiver fora.

---

# A entrega de vocês — o alvo da semana

```
  [ ] App compila e roda em Android e desktop
  [ ] Uma tela com componente próprio e estado elevado
  [ ] ktlintCheck e detekt limpos no GitHub Actions
```

Vale quase metade da nota da Sprint 0 (projeto funcional e CI, mais a primeira tela). O resto é a proposta e as justificativas de plataforma e backend.

> Não precisa de app publicado em loja, nem de rede. Precisa compilar, ter a tela, e o CI verde.

---

# Oficina — reutilizar e verificar <span class="pill-blue">alvo desktop</span>

Partindo da tela da segunda:

```
  1. Extraia uma Secao(titulo) { … } com slot, e use no TelaAcervo
  2. Troque cores/tamanhos crus por MaterialTheme.typography/colorScheme
  3. Reescreva um trecho com let/apply, no lugar de if + temporária
  4. Ligue ktlintCheck e detekt no workflow; rode e conserte o que apontarem
```

> Deixem o `detekt` apontar de propósito uma função longa, e depois quebrem-na. Ver a ferramenta pegar é metade do aprendizado.

---

# As tarefas da Sprint 0

O enunciado de cada entrega está em `docs/SPRINT-0-TAREFAS.md` — uma tarefa por cartão, com *pronto quando* e o peso na rubrica.

| Ajuste recente | Detalhe |
|---|---|
| Backlog: mínimo 5 histórias (antes 10) | ≥ 3 estimadas, todas priorizadas |
| `docs/proposta.md`: até 5 páginas (antes 3) | as 7 seções do guia |

> Criem uma issue por tarefa. O guia completo, com templates, continua em `docs/SPRINT-0.md`.

---

# Próximos passos

**Até a entrega (11/09)**

- App compilando em Android e desktop, com a primeira tela
- Componente próprio e reutilizável, estado elevado, ao menos dois `@Preview`
- `ktlintCheck` e `detekt` verdes no GitHub Actions
- Proposta com plataforma-alvo e backend justificados, e o backlog (≥ 5 histórias)

> 09/09 é encontro online, no horário da aula, para dúvidas sobre o ambiente e a proposta.

---

# Referências da semana

**Compose e componentes**

- *Thinking in Compose* · `developer.android.com/develop/ui/compose/mental-model`
- *State in Compose* · `developer.android.com/develop/ui/compose/state`
- *Compose layout* e *slots* · `developer.android.com/develop/ui/compose/layouts`

**Tema, Kotlin e ferramentas**

- Material 3 · `m3.material.io` · Compose Multiplatform · `kotlinlang.org/compose-multiplatform`
- Funções de escopo · `kotlinlang.org/docs/scope-functions.html`
- Catálogo de versões · `docs.gradle.org` · ktlint · `ktlint.github.io` · detekt · `detekt.dev`

**Disciplina e projeto de exemplo**

- `github.com/fmarquesfilho/sistemas-moveis-2026-2`
- MUSI · `github.com/fmarquesfilho/musi` — `app/`, `shared/`, `gradle/libs.versions.toml`
