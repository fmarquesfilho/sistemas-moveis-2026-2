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

## Camadas, Koin, dados reais, cache e testes de ViewModel

DIM0524 — Turma 01 · Sprint 2 · aula de 21/10

Prof. Fernando · UFRN · 2026.2

---

# Onde paramos

```
  passo 11   o estado no ViewModel, com StateFlow
  passo 12   carregar numa corrotina; os quatro estados como classe selada
```

| Passo | Hoje |
|---|---|
| 13 | Camadas (domínio, dados, apresentação) e injeção de dependência com Koin |
| 14 | Dados reais: o app chama a API de Tarefas de Web II com o Ktor Client |
| 15 | Abrir sem rede: a última lista guardada no aparelho, com DataStore |
| 16 | Testes de ViewModel com Turbine e dublês |

**Branch `aula`:** `./passo.sh 12`. Para os passos 14 e 15, a API de Web II precisa estar no ar:

```bash
cd web2-2026-2/exemplos && docker compose up --build    # API, banco e serviço Go
```

---

# Passo 13

## Camadas e injeção de dependência com Koin

---

# Três camadas, em pacotes

```
br.ufrn.exemplo.tarefas
├── App.kt, Modulos.kt        tema, navegação e a raiz de composição
├── dominio/                  Tarefa, regras, a porta RepositorioDeTarefas
├── dados/                    as implementações do repositório (memória, rede, cache)
└── apresentacao/             TarefasViewModel, EstadoTela, as telas
```

| Camada | Pode depender de | Não pode depender de |
|---|---|---|
| domínio | só Kotlin | Compose, Ktor, DataStore, Koin |
| dados | domínio, bibliotecas de rede e de disco | apresentação |
| apresentação | domínio, Compose, ViewModel | detalhes de dados (Ktor, DataStore) |

- O ViewModel conhece a interface `RepositorioDeTarefas`, e não quem a implementa
- É o critério "Estado e arquitetura": domínio sem Compose nem infraestrutura, repositórios por interface, sem lógica de negócio dentro de `@Composable`

---

# Koin: quem monta as peças

```kotlin
// Modulos.kt: o único lugar que conhece as três camadas
val moduloTarefas = module {
    single<RepositorioDeTarefas> { RepositorioEmMemoria() }
    viewModelOf(::TarefasViewModel)          // o Koin passa o repositório ao construtor
}
```

```kotlin
class TarefasViewModel(private val repositorio: RepositorioDeTarefas) : ViewModel()   // recebe, não cria

@Composable
fun App() {
    KoinApplication(application = { modules(moduloTarefas) }) { ... }
}

@Composable
fun Conteudo(largo: Boolean, viewModel: TarefasViewModel = koinViewModel()) { ... }
```

- Trocar a implementação (memória → rede) é trocar uma linha no módulo
- Nos testes, o ViewModel é criado com `new`, com o repositório que o teste quiser: sem Koin
- `ModulosTest` confere que o módulo consegue montar o ViewModel

📖 **Ref.** [Koin — Compose Multiplatform](https://insert-koin.io/docs/reference/koin-compose/compose)

---

# Passo 14

## Dados reais com o Ktor Client

---

# DTO separado da entidade

```kotlin
// dados/: o formato do JSON da API
@Serializable
data class TarefaDto(val id: Int, val titulo: String, val feita: Boolean = false)

fun TarefaDto.paraDominio() = Tarefa(id = id, titulo = titulo, feita = feita)
```

```kotlin
class RepositorioRemoto(private val cliente: HttpClient, private val enderecoDaApi: String) : RepositorioDeTarefas {

    override suspend fun listar(): List<Tarefa> = traduzindoFalhas {
        cliente.get("$enderecoDaApi/tarefas").body<List<TarefaDto>>().map { it.paraDominio() }
    }

    override suspend fun adicionar(titulo: String): Tarefa = traduzindoFalhas {
        cliente.post("$enderecoDaApi/tarefas") {
            contentType(ContentType.Application.Json)
            setBody(NovaTarefaDto(titulo))
        }.body<TarefaDto>().paraDominio()
    }
}
```

Se a API mudar um nome de campo, mudam o DTO e a conversão; o domínio e as telas continuam iguais.

📖 **Ref.** [Ktor — Client](https://ktor.io/docs/client-create-new-application.html)

---

# Erros de rede com nome

```kotlin
sealed class FalhaNoRepositorio(mensagem: String) : Exception(mensagem) {      // no domínio
    class SemConexao : FalhaNoRepositorio("sem conexão com o servidor")
    class RespostaInvalida(detalhe: String) : FalhaNoRepositorio("resposta inválida: $detalhe")
}
```

| O que aconteceu | Exceção do Ktor | Falha do domínio | Na tela |
|---|---|---|---|
| API fora do ar, sem rede | `IOException` | `SemConexao` | "Sem conexão com o servidor. Confira a rede e tente de novo." |
| status 4xx ou 5xx | `ResponseException` (`expectSuccess = true`) | `RespostaInvalida` | "O servidor respondeu algo inesperado (...)" |
| JSON fora do formato | `ContentConvertException` | `RespostaInvalida` | idem |

- Cada falha tem um estado de tela próprio: é o que a rubrica pede em "Dados reais e erro de rede"
- O ViewModel não conhece o Ktor: só as falhas do domínio
- Salvar que falha não apaga a lista: vira um aviso acima dela

---

# Onde está a API

```kotlin
expect val enderecoDaApi: String?                    // commonMain; nulo = sem API, repositório em memória

actual val enderecoDaApi: String? = when (val valor = System.getenv("TAREFAS_API")) {   // desktop
    null, "" -> "http://localhost:8080"
    "memoria" -> null                                 // TAREFAS_API=memoria: roda sem a API
    else -> valor
}
actual val enderecoDaApi: String? = "http://10.0.2.2:8080"   // Android (emulador)
```

| Onde o app roda | Endereço da API |
|---|---|
| desktop, na mesma máquina | `http://localhost:8080` |
| emulador Android | `http://10.0.2.2:8080`: o computador que roda o emulador |
| sem a API (Codespace, laboratório) | `TAREFAS_API=memoria` ou `mise run app:sem-api`: repositório em memória |
| celular na mesma rede Wi-Fi | o IP do computador, por exemplo `http://192.168.0.10:8080` |

No Android: permissão `INTERNET` e, para `http://` local, `usesCleartextTraffic="true"` (num app publicado, a API usa `https` e essa linha sai).

**Demonstração:** a API de Web II no ar, `./passo.sh 14`, `mise run app`: as tarefas vêm do banco. Pare a API e toque em **Tentar de novo**.

---

# Passo 15

## Abrir sem rede

---

# Um repositório que usa outro

```kotlin
class RepositorioComCache(
    private val remoto: RepositorioDeTarefas,
    private val cache: CacheDeTarefas,
) : RepositorioDeTarefas {

    override suspend fun listar(): Listagem = try {
        remoto.listar().also { cache.salvar(it.tarefas) }          // guarda cada lista recebida
    } catch (e: FalhaNoRepositorio.SemConexao) {
        val guardadas = cache.ler() ?: throw e                      // nunca carregou: o erro continua
        Listagem(guardadas, doCache = true)
    }
}
```

```kotlin
class CacheDeTarefas(private val dataStore: DataStore<Preferences>) {
    suspend fun salvar(tarefas: List<Tarefa>) {
        dataStore.edit { it[chave] = Json.encodeToString(tarefas.map { TarefaDto(it.id, it.titulo, it.feita) }) }
    }
    suspend fun ler(): List<Tarefa>? = dataStore.data.first()[chave]?.let { ... }
}
```

- O ViewModel não sabe que existe cache: só recebe `doCache = true`, e a tela mostra o aviso
- Para listas grandes ou consultas, um banco no aparelho (Room ou SQLDelight); para uma lista pequena, o DataStore basta

📖 **Ref.** [DataStore — Kotlin Multiplatform](https://developer.android.com/kotlin/multiplatform/datastore)

---

# Onde fica o arquivo

```kotlin
fun moduloTarefas(diretorioDeDados: String) = module {
    single { criarClienteHttp() }
    single { CacheDeTarefas(criarDataStore(diretorioDeDados)) }        // um DataStore por arquivo
    single<RepositorioDeTarefas> {
        val endereco = enderecoDaApi
        if (endereco == null) RepositorioEmMemoria()
        else RepositorioComCache(RepositorioRemoto(get(), endereco), get())
    }
    viewModelOf(::TarefasViewModel)
}
```

| Plataforma | `App(diretorioDeDados = ...)` |
|---|---|
| Android (`MainActivity`) | `filesDir.path`, a pasta privada do app |
| desktop (`main.kt`) | `~/.tarefas-compose` |

**Demonstração:** abrir o app com a API no ar; fechar; parar a API (`docker compose stop api-ktor`); abrir de novo: "Sem conexão: mostrando a última lista salva."

---

# Passo 16

## Testes de ViewModel com Turbine

---

# Um dublê e o relógio virtual

```kotlin
class RepositorioFalso(var listar: () -> Listagem = { Listagem(listOf(Tarefa(1, "Estudar Flow"))) }) : RepositorioDeTarefas {
    override suspend fun listar(): Listagem { delay(500); return listar.invoke() }   // 500 ms de "rede"
    ...
}
```

```kotlin
@Test
fun carregarPassaPorCarregandoEChegaASucesso() = runTest {
    Dispatchers.setMain(StandardTestDispatcher(testScheduler))   // o Main usa o relógio do teste
    TarefasViewModel(RepositorioFalso()).estado.test {           // Turbine: observa o StateFlow
        assertEquals(EstadoTela.Carregando, awaitItem().tela)    // o estado intermediário aparece
        assertIs<EstadoTela.Sucesso>(awaitItem().tela)           // e os 500 ms passam sem esperar
    }
}
```

- `awaitItem()` é o próximo estado emitido, na ordem: o teste vê a sequência, e não só o fim
- O tempo é virtual: os 500 ms do dublê passam na hora

📖 **Ref.** [Turbine](https://github.com/cashapp/turbine) · [Android — Testing coroutines](https://developer.android.com/kotlin/coroutines/test)

---

# A suíte, no fim do Passo 16

| Arquivo | Testes | O que cobre |
|---|---|---|
| `TarefasViewModelTest` | 7 | carregando → sucesso, vazio, sem conexão, resposta inválida, tentar de novo, lista do cache, adicionar |
| `TelasTest` | 7 | formulário, linha alternável, cabeçalho, navegação, janela larga, erro com tentar de novo, aviso de cache |
| `RepositorioRemotoTest` | 4 | JSON → tarefas, servidor fora, 500, JSON fora do formato (`MockEngine`) |
| `RepositorioComCacheTest` | 3 | guarda, devolve sem conexão, erro sem nada guardado |
| `RegrasTest` e `ModulosTest` | 4 | regras puras e o módulo do Koin |

```bash
./gradlew :composeApp:desktopTest      # 25 testes, no alvo desktop, sem emulador
```

A rubrica pede ≥ 5 de interface e ≥ 5 de ViewModel, verdes no CI.

---

# O que a Sprint 2 cobra

| Critério | Peso | O que mostrar |
|---|---|---|
| Estado e arquitetura | 25% | ViewModel com `StateFlow`; camadas em `commonMain`; domínio sem Compose nem infraestrutura; repositórios por interface |
| Estados da interface | 15% | carregando, erro, vazio e sucesso, por classe selada, na tela |
| Dados reais e erro de rede | 25% | API real, Ktor Client, DTO separado; falta de conexão e resposta inválida com estado próprio |
| Persistência local | 10% | o app abre sem rede mostrando o último conteúdo |
| Testes | 15% | ≥ 5 de interface e ≥ 5 de ViewModel com Turbine e dublês, verdes no CI |
| App e documentação | 10% | sem crash; `docs/arquitetura.md`; README; APK numa release |

Prova em **11/11**; *daily meetings* em 23 e 25/11; **entrega em 30/11**.

---

# Onde estudar depois

- Leitura da sprint: `leituras/moveis-s2.md`, capítulos 5 a 9
- Passos: `exemplos/tarefas-compose/PASSOS.md` · branch `aula`, tags `passo-13` a `passo-16`
- [Koin — Compose Multiplatform](https://insert-koin.io/docs/reference/koin-compose/compose)
- [Ktor — Client](https://ktor.io/docs/client-create-new-application.html)
- [DataStore — Kotlin Multiplatform](https://developer.android.com/kotlin/multiplatform/datastore)
- [Turbine](https://github.com/cashapp/turbine) · [Android — Testing coroutines](https://developer.android.com/kotlin/coroutines/test)
