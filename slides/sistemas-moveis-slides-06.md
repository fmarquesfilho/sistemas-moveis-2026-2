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

## Corrotinas, Flow e ViewModel

DIM0524 — Turma 01 · Sprint 2 · aula de 19/10

Prof. Fernando · UFRN · 2026.2

---

# A Sprint 2, a última

| | Data |
|---|---|
| Conteúdo | hoje (passos 11 e 12) e quarta, 21/10 (passos 13 a 16) |
| Acompanhamento online | 26/10, 09/11 e 16/11 |
| Prova escrita | **11/11 (quarta)**, em laboratório |
| *Daily meetings* online | 23 e 25/11 (presencial, se preferirem, em 18/11) |
| **Entrega final** | **30/11, 23:59** |

| Critério | Peso | Aula |
|---|---|---|
| Estado e arquitetura | 25% | hoje e quarta |
| Estados da interface | 15% | hoje |
| Dados reais e erro de rede | 25% | quarta |
| Persistência local | 10% | quarta |
| Testes (interface e ViewModel) | 15% | quarta |
| App e documentação | 10% | — |

---

# Onde vamos chegar

```
  ┌─────────────── apresentação ───────────────┐   ┌──── dados ────┐
  │  Telas (@Composable)  ◄── estado ──  ViewModel  ──► Repositório ──► API de Tarefas (Web II)
  │        │                                ▲     │   │   + cache   │      GET / POST /tarefas
  │        └──────── eventos ───────────────┘     │   └──────┬──────┘
  └────────────────────────────────────────────────┘          ▼
                                                     DataStore (no aparelho)
```

- O estado sai da tela e vai para um ViewModel, que sobrevive à rotação
- As tarefas vêm da API de Web II, pela rede; sem rede, da última lista guardada
- Cada estado da tela (carregando, vazio, erro, sucesso) é um caso de uma classe selada

**Branch `aula`:** `./passo.sh 10` é onde a Sprint 1 terminou; hoje vamos até o 12.

---

# Corrotinas em uma página

Já vistas na aula de Kotlin (`suspend`). O essencial para hoje:

| Peça | O que faz |
|---|---|
| `suspend fun` | uma função que pode esperar (rede, disco) sem travar a thread |
| `launch { }` | inicia uma corrotina e segue em frente; devolve um `Job` |
| escopo (`CoroutineScope`) | dono das corrotinas: quando ele termina, as corrotinas dele são canceladas |
| `viewModelScope` | o escopo do ViewModel: cancelado quando o ViewModel deixa de existir |
| `Dispatchers.Main` | a thread da interface; as corrotinas do ViewModel começam nela |

```kotlin
viewModelScope.launch {
    val tarefas = repositorio.listar()   // suspende aqui; a tela continua respondendo
    _estado.update { it.copy(...) }      // volta e atualiza o estado
}
```

> Concorrência estruturada: toda corrotina tem um dono. Fechar a tela cancela o que ela pediu, sem vazamento.

📖 **Ref.** [Android — Kotlin coroutines on Android](https://developer.android.com/kotlin/coroutines)

---

# Flow e StateFlow

| | O que é | No app |
|---|---|---|
| `Flow<T>` | uma sequência de valores que chegam ao longo do tempo | a lista atualizada a cada mudança |
| `StateFlow<T>` | um `Flow` que sempre tem um valor atual, e emite quando ele muda | o estado da tela |
| `MutableStateFlow<T>` | o `StateFlow` que pode ser alterado | privado, dentro do ViewModel |

```kotlin
private val _estado = MutableStateFlow(EstadoLista())     // só o ViewModel altera
val estado: StateFlow<EstadoLista> = _estado.asStateFlow()  // a tela só lê

_estado.update { it.copy(texto = novo) }                  // um valor novo, imutável
```

- O estado é uma `data class` imutável: mudar é criar uma cópia com `copy`
- `update { }` faz a troca de forma segura, mesmo com várias corrotinas

📖 **Ref.** [Android — StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)

---

# Passo 11

## O estado sai da tela: ViewModel com `StateFlow`

---

# O ViewModel

```kotlin
data class EstadoLista(
    val tarefas: List<Tarefa> = tarefasIniciais,
    val texto: String = "",
)

class TarefasViewModel : ViewModel() {
    private val _estado = MutableStateFlow(EstadoLista())
    val estado: StateFlow<EstadoLista> = _estado.asStateFlow()
    private var proximoId = 3

    fun mudarTexto(texto: String) = _estado.update { it.copy(texto = texto) }

    fun adicionar() = _estado.update {
        if (!tituloValido(it.texto)) it
        else it.copy(tarefas = it.tarefas.comNova(proximoId++, it.texto), texto = "")
    }

    fun alternar(id: Int) = _estado.update { it.copy(tarefas = it.tarefas.alternando(id)) }
}
```

As regras (`tituloValido`, `comNova`, `alternando`) são as funções puras da Sprint 1: o ViewModel só as chama.

---

# A tela lê o estado e chama eventos

```kotlin
@Composable
fun Conteudo(
    largo: Boolean,
    viewModel: TarefasViewModel = viewModel { TarefasViewModel() },   // vem de fora: testável
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()

    TelaLista(
        estado.tarefas, estado.texto,
        viewModel::mudarTexto, viewModel::adicionar, viewModel::alternar,   // referências a funções
        onAbrir = { ... },
    )
}
```

| Antes (Sprint 1) | Agora |
|---|---|
| `var tarefas by remember { mutableStateOf(...) }` na tela | `StateFlow` no ViewModel |
| perdido ao girar o aparelho, se não for `rememberSaveable` | sobrevive à rotação e à troca de destino |

`collectAsStateWithLifecycle` para de coletar quando a tela sai de vista.

📖 **Ref.** [Compose Multiplatform — ViewModel](https://kotlinlang.org/docs/multiplatform/compose-viewmodel.html)

---

# Passo 12

## Carregar sem travar a tela: estado selado

---

# Os quatro estados como classe selada

```kotlin
sealed interface EstadoTela {
    data object Carregando : EstadoTela
    data object Vazio : EstadoTela
    data class Sucesso(val tarefas: List<Tarefa>) : EstadoTela
    data class Erro(val mensagem: String) : EstadoTela
}
```

```kotlin
when (val tela = estado.tela) {                  // sem else: o compilador confere os quatro
    EstadoTela.Carregando -> TelaCarregando()
    is EstadoTela.Erro -> TelaErro(tela.mensagem, onTentarDeNovo = viewModel::carregar)
    EstadoTela.Vazio, is EstadoTela.Sucesso -> TelaLista(estado.tarefas, ...)
}
```

- Classe selada: todos os casos são conhecidos na compilação. Um quinto estado faz o `when` deixar de compilar até alguém decidir como mostrá-lo
- Dentro do ramo `is EstadoTela.Erro`, `tela` já é um `Erro` (*smart cast*): `tela.mensagem` existe
- É o critério "Estados da interface" da rubrica: carregando, erro, vazio e sucesso

---

# Carregar numa corrotina

```kotlin
interface RepositorioDeTarefas {
    suspend fun listar(): List<Tarefa>
    suspend fun adicionar(titulo: String): Tarefa
}

fun carregar() {
    _estado.update { it.copy(tela = EstadoTela.Carregando) }
    viewModelScope.launch {
        val tela = try {
            val tarefas = repositorio.listar()                  // 800 ms no repositório em memória
            if (tarefas.isEmpty()) EstadoTela.Vazio else EstadoTela.Sucesso(tarefas)
        } catch (e: CancellationException) {
            throw e                                              // cancelamento não é erro
        } catch (e: Exception) {
            EstadoTela.Erro("Não foi possível carregar as tarefas.")
        }
        _estado.update { it.copy(tela = tela) }
    }
}
```

- O repositório em memória tem 800 ms de demora e um `falhar`: dá para ver os quatro estados sem rede
- `CancellationException` é como a corrotina é cancelada: capturá-la e seguir em frente impede o cancelamento

---

# Demonstração

```bash
./passo.sh 12
mise run app          # ou: ./gradlew :composeApp:hotRunDesktop --auto
```

1. O app abre com o indicador de carregamento e, 800 ms depois, a lista
2. No `RepositorioEmMemoria`, `falhar = true`: aparece a mensagem e o botão **Tentar de novo**
3. Com a lista inicial vazia: aparece "Nenhuma tarefa ainda"
4. Gire o emulador (ou redimensione a janela): o texto digitado continua lá

Os testes de ViewModel desta etapa trocam o `Dispatchers.Main` por um despachante de teste:

```kotlin
@BeforeTest fun trocarMain() = Dispatchers.setMain(UnconfinedTestDispatcher())
@AfterTest fun restaurarMain() = Dispatchers.resetMain()
```

---

# Na quarta, 21/10

| Passo | Assunto |
|---|---|
| 13 | Camadas (domínio, dados, apresentação) e injeção de dependência com Koin |
| 14 | Dados reais: Ktor Client, DTO e erros de rede com nome, chamando a API de Web II |
| 15 | Abrir sem rede: a última lista guardada com DataStore |
| 16 | Testes de ViewModel com Turbine e dublês |

Até lá, no projeto do grupo:

- levar o estado de uma tela para um ViewModel com `StateFlow`
- modelar os estados dessa tela como classe selada, com os quatro casos
- escolher a API real que o app vai consumir

---

# Onde estudar depois

- Leitura da sprint: `leituras/moveis-s2.md`, capítulos 1 a 4
- Passos: `exemplos/tarefas-compose/PASSOS.md` · branch `aula`, tags `passo-11` e `passo-12`
- [Android — Kotlin coroutines on Android](https://developer.android.com/kotlin/coroutines)
- [Android — StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
- [Compose Multiplatform — ViewModel](https://kotlinlang.org/docs/multiplatform/compose-viewmodel.html)
- [Kotlin — Sealed classes](https://kotlinlang.org/docs/sealed-classes.html) · [Coroutines basics](https://kotlinlang.org/docs/coroutines-basics.html)
