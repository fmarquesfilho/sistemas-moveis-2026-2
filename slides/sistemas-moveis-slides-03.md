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

## Listas, formulários e Material 3

DIM0524 — Turma 01 · Sprint 1 · 14/09

Prof. Fernando · UFRN · 2026.2

---

# Roteiro da semana

**Segunda, 14/09**

| Bloco | O que vemos |
|---|---|
| Ambiente | Android Studio no laboratório |
| Fecha a Sprint 0 | `@Composable`, estado, componentes, Material 3 |
| Abre a Sprint 1 | Listas com `LazyColumn`, formulários e validação |
| Tema | Material 3, claro e escuro |

**Quarta, 16/09 — Acompanhamento online** · 🚀 **Entrega da Sprint 0, 23:59**
**Segunda, 21/09** — Responsividade, acessibilidade, navegação e testes

> Hoje construímos uma tela do zero, em passos.

---

# Android Studio

O laboratório tem Android Studio instalado — vamos usá-lo no lugar do Codespaces.

| | Codespaces | Android Studio (lab) |
|---|---|---|
| Hot Reload | instável | funciona |
| `@Preview` | fraco | funciona |
| Emulador Android | não | sim |

> No Codespaces, Hot Reload e `@Preview` não funcionam bem.

---

# Como rodar hoje

No Android Studio, com o projeto aberto:

- Emulador Android: run configuration `composeApp` → botão Run ▸.
- Desktop (janela JVM): `./gradlew :composeApp:run`.

A UI compartilhada fica em `composeApp/src/commonMain/kotlin`. A mesma `App()` roda no Android e no Desktop. O `@Preview` mostra a tela sem rodar o app.

> Para iterar rápido no emulador, use o Apply Changes; no desktop, edite e rode de novo.

---

# Onde paramos

Na Sprint 0 vimos os fundamentos:

```
  KMP: módulos shared/app e a regra de dependência
  Compose: @Composable, recomposição, estado
  Componentes próprios · Material 3 · Modificadores
```

Hoje concluímos o que faltou e damos o passo seguinte: uma tela com lista e formulário.

---

# Recap: os 3 conceitos que sustentam tudo

| Conceito | Em uma frase |
|---|---|
| `@Composable` | Função que descreve a UI a partir de dados |
| Recomposição | Mudou o estado → o Compose redesenha o que depende dele |
| Estado elevado | O estado sobe; o componente recebe dados e devolve eventos |

```kotlin
var contador by remember { mutableStateOf(0) }
Button(onClick = { contador++ }) { Text("Cliquei $contador") }
```

> `remember { mutableStateOf(...) }` guarda o estado entre recomposições.

---

# Entendendo: lambdas, `{ }` e `it`

Compose usa muito `{ }`. Quase sempre é uma lambda — um bloco de código passado como argumento.

```kotlin
Button(onClick = { contador++ }) { Text("Cliquei") }
//              └── lambda: o que fazer no clique
//                                   └── trailing lambda: o conteúdo do botão
```

- `() -> Unit` é o tipo de uma função sem parâmetros que nada retorna (`Unit` ≈ `void`).
- Se a lambda é o último argumento, ela sai dos parênteses (*trailing lambda*) — daí `Column { }`, `Card { }`, `LazyColumn { }`.
- `it` é o nome automático do parâmetro único: em `onValueChange = { texto = it }`, o `it` é o novo texto.

> **Kotlin para quem conhece Java:** a lambda é como a do Java (`() -> {}`); o Kotlin deixa tirá-la dos parênteses e usar `it`. É o que dá o visual de DSL do Compose.

📖 **Ref.** [Kotlin — lambdas](https://kotlinlang.org/docs/lambdas.html) · [trailing lambdas](https://kotlinlang.org/docs/lambdas.html#passing-trailing-lambdas)

---

# Entendendo: estado com `by`

```kotlin
var contador by remember { mutableStateOf(0) }
```

Três peças numa linha só:

- `mutableStateOf(0)` — cria um estado observável: quando muda, o Compose recompõe quem o lê.
- `remember { }` — guarda esse estado entre recomposições (sem ele, voltaria a 0 a cada redesenho).
- `by` — propriedade delegada: ler `contador` devolve o valor; `contador = x` atualiza o estado. Sem o `by`, seria `contador.value`.

> **Kotlin para quem conhece Java:** o `by` delega leitura e escrita a outro objeto (aqui, o estado). Em Java você chamaria `get()/set()`; o `by` faz `contador` parecer uma variável comum.

📖 **Ref.** [Kotlin — delegated properties](https://kotlinlang.org/docs/delegated-properties.html) · [Compose — state](https://developer.android.com/develop/ui/compose/state)

---

# Entendendo: `data class`

```kotlin
data class Tarefa(val id: Int, val titulo: String, val feita: Boolean = false)
```

- Uma classe para guardar dados. O `data` gera `equals`, `hashCode`, `toString` e `copy()`.
- `val` é imutável (como `final`); `var` é mutável. Aqui é tudo `val`.
- `= false` é um valor padrão: dá para criar `Tarefa(1, "X")` sem informar `feita`.
- `copy()` cria uma cópia mudando um campo: `tarefa.copy(feita = true)`.

> **Kotlin para quem conhece Java:** é como um `record`, com o acréscimo do `copy()`, usado para atualizar estado imutável — `lista.map { it.copy(...) }`.

📖 **Ref.** [Kotlin — data classes](https://kotlinlang.org/docs/data-classes.html)

---

# Entendendo: por que `val` e `copy`?

A tela é função do estado. O que muda é o estado (um `var` observável); os dados que ele guarda são imutáveis (`val`).

```kotlin
var tarefas by remember { mutableStateOf(listOf<Tarefa>()) }

// map percorre a lista e devolve uma lista NOVA:
tarefas = tarefas.map {
  if (it.id == alvo)             // it = tarefa da vez; é a alvo?
    it.copy(feita = !it.feita)   // sim: cópia com 'feita' invertido
  else
    it                           // não: a mesma tarefa, sem mudar
}
// atribuir de volta a 'tarefas' dispara a recomposição
```

- Só a tarefa-alvo muda; as outras voltam como `it` (mesmos objetos). Com `key`, o Compose redesenha só o item alterado.
- Nada é mutado no lugar: `map` e `copy` criam novos; a lista antiga fica intacta.

> "Por que não `var`?" Mutar um campo por dentro não avisa o Compose. O padrão é dado imutável e reatribuir o estado.

📖 **Ref.** [Compose — state](https://developer.android.com/develop/ui/compose/state) · [Kotlin — data classes](https://kotlinlang.org/docs/data-classes.html)

---

# O exemplo de hoje: **Tarefas**

Uma tela construída em 5 passos — cada um roda:

| Passo | O que entra |
|---|---|
| 1 | Um `@Composable` com estado |
| 2 | Componente próprio reutilizável (`CartaoTarefa`) |
| 3 | Lista com `LazyColumn` |
| 4 | Formulário com validação |
| 5 | Material 3 e tema (claro/escuro) |

Código pronto para consulta: `exemplos/tarefas-compose/` (ver `PASSOS.md`).

---

<!-- _class: lead -->

# Passo 1

## Um `@Composable` com estado

---

# Passo 1 — a primeira tela

```kotlin
@Composable
fun App() {
  MaterialTheme {
    var contador by remember { mutableStateOf(0) }
    Button(onClick = { contador++ }) {
      Text("Cliquei $contador")
    }
  }
}
```

- `App()` é o ponto de entrada da UI — o que o `MainActivity` monta.
- `remember { mutableStateOf(0) }` guarda o estado; clicar recompõe o texto.

> Ponto de partida da tela: uma função `@Composable` com estado. Nos próximos passos ela vira uma lista de tarefas.

📖 **Ref.** [Compose — state](https://developer.android.com/develop/ui/compose/state)

---

<!-- _class: lead -->

# Passo 2

## Componente próprio, estado elevado

---

# Passo 2 — `CartaoTarefa`

```kotlin
data class Tarefa(val id: Int, val titulo: String, val feita: Boolean = false)

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
```

> Estado elevado: o cartão recebe a `tarefa` e devolve o evento `onAlternar`, sem guardar estado interno. Fica reutilizável e testável.

📖 **Ref.** [Compose — state e state hoisting](https://developer.android.com/develop/ui/compose/state)

---

<!-- _class: lead -->

# Passo 3

## Lista com `LazyColumn`

---

# Passo 3 — `LazyColumn`

```kotlin
LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
  items(tarefas, key = { it.id }) { tarefa ->  // tarefa = a linha clicada
    CartaoTarefa(tarefa) {                      // ação ao tocar no checkbox
      tarefas = tarefas.map {                   // it = cada item da lista
        if (it.id == tarefa.id)                 // é a linha clicada?
          it.copy(feita = !it.feita)            // sim: cópia com 'feita' invertido
        else it                                 // não: mantém o item
      }
    }
  }
}
```

- `LazyColumn` cria só os itens visíveis — bom para listas grandes.
- `tarefa` vem do `items`; `it` é cada item dentro do `map`.

> Uma `Column` com `verticalScroll` cria tudo de uma vez e pode apresentar problemas em performance.
> Para listas que podem ser grandes, melhor usar o `LazyColumn` que renderiza apenas o que é necessário.

📖 **Ref.** [Compose — lazy lists](https://developer.android.com/develop/ui/compose/lists)

---

# Entendendo: `key` na LazyColumn

```kotlin
items(tarefas, key = { it.id }) { tarefa ->
  CartaoTarefa(tarefa) { /* ... */ }
}
```

- `key = { it.id }` dá a cada item uma identidade estável (o `id`), única na lista.
- Sem `key`, o Compose identifica os itens pela posição (índice). Se a lista muda — inserir, remover, reordenar — os itens trocam de posição e o estado de uma linha pode ir parar em outra.
- Com `key`, o Compose casa cada linha pelo `id`: preserva o estado das que ficaram e recompõe só a que mudou.

> Exemplo: remova a 1ª tarefa. Sem `key`, a 2ª herda o estado da 1ª (rolagem, animação); com `key`, cada uma mantém o seu. Use `key` sempre que a lista puder mudar.

📖 **Ref.** [Compose — lazy lists (chaves de item)](https://developer.android.com/develop/ui/compose/lists#item-keys)

---

<!-- _class: lead -->

# Passo 4

## Formulário com validação

---

# Passo 4 — formulário

```kotlin
var texto by remember { mutableStateOf("") }
val valido = texto.isNotBlank()

Row(verticalAlignment = Alignment.CenterVertically) {
  OutlinedTextField(
    value = texto,
    onValueChange = { texto = it },
    label = { Text("Nova tarefa") },
    isError = texto.isNotEmpty() && !valido,
    modifier = Modifier.weight(1f),
  )
  Button(onClick = { /* adiciona */ }, enabled = valido) { Text("Adicionar") }
}
```

> A validação vem do estado (`valido`). O botão habilita quando o título é válido, e o campo sinaliza erro.

📖 **Ref.** [Compose — campos de texto](https://developer.android.com/develop/ui/compose/text/user-input) · [Compose — state](https://developer.android.com/develop/ui/compose/state) · [Documentação do componente de UI](https://m3.material.io/components/text-fields/overview#outlined-text-field)

---

<!-- _class: lead -->

# Passo 5

## Material 3 e tema

---

# Passo 5 — Material 3

Os componentes (`Button`, `Card`, `OutlinedTextField`) já seguem o Material 3. O tema define cor e tipografia:

```kotlin
MaterialTheme(
  colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
) {
  App()
}
```

- Cor e tipografia vêm do `MaterialTheme`, não fixadas à mão.
- Modo claro e escuro com pouca configuração.

> No emulador, alterne o tema do sistema e veja a tela responder, sem mudar uma linha da `App()`.

📖 **Ref.** [Material 3 no Compose](https://developer.android.com/develop/ui/compose/designsystems/material3)

---

# `@Preview` — ver sem rodar

Em `commonMain`, anote uma função sem parâmetros:

```kotlin
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AppPreview() { App() }
```

O Android Studio renderiza a tela ao lado do código.

> No ambiente local, o preview responde na hora — sem esperar o emulador.

📖 **Ref.** [Compose — @Preview](https://developer.android.com/develop/ui/compose/tooling/previews)

---

# Boas práticas de hoje

| Prática | Por quê |
|---|---|
| Estado elevado | Componentes reutilizáveis e testáveis |
| Componente sem estado interno | Recebe dados, devolve eventos |
| `key` em listas | Rolagem e desempenho estáveis |
| Cor/tipografia via `MaterialTheme` | Tema consistente, claro e escuro |

---

# Para a próxima aula (21/09)

- **Responsividade e adaptatividade**: classes de tamanho de janela
- **Acessibilidade**: descrições, contraste, alvos de toque
- **Navegação**: Navigation Compose — rotas tipadas, argumentos, deep links
- **Testes** em Compose Multiplatform

No dia 16/09 é o acompanhamento online de projeto. 🚀 Entrega da Sprint 0 nesse dia, 23:59.

> Lembrete: a primeira tela da Sprint 0 agora é opcional, mas com o que vimos hoje vocês já podem construí-la.

---

# Onde estudar depois

| Fonte | Foco |
|---|---|
| `kotlinlang.org/docs/multiplatform` · *Compose Multiplatform* | Criar o primeiro app, alvos e execução |
| `developer.android.com/develop/ui/compose` | Compose e listas |
| `m3.material.io` | Material 3: cor, tipografia, componentes |

