# Tarefas da Sprint 2 — DIM0524

Estes são os enunciados das tarefas da Sprint 2, prontos para virar cartões no GitHub Projects. Cada um tem um objetivo, o que fazer, e o *pronto quando* alinhado à rubrica.

O **como** está em [SPRINT-2.md](SPRINT-2.md) e na leitura da sprint ([`leituras/moveis-s2.md`](../leituras/moveis-s2.md)): as tarefas apontam para a seção certa em vez de repeti-la. Os pesos vêm de [RUBRICAS.md](RUBRICAS.md#sprint-2). Prazo em [CRONOGRAMA.md](CRONOGRAMA.md#visão-geral): **30/11, 23:59**.

| # | Tarefa | Critério da rubrica |
|---|---|---|
| T1 | Quebrar o trabalho em cartões da sprint | Atividade no repositório |
| T2 | Levar o estado de cada tela para um ViewModel | Estado e arquitetura (25%) |
| T3 | Modelar os estados da tela como classe selada | Estados da interface (15%) |
| T4 | Separar as camadas e injetar as dependências | Estado e arquitetura (25%) |
| T5 | Buscar os dados no backend com o Ktor Client | Dados reais e erro de rede (25%) |
| T6 | Tratar falta de conexão e resposta inválida | Dados reais e erro de rede (25%) |
| T7 | Guardar os dados no aparelho | Persistência local (10%) |
| T8 | Escrever os testes de ViewModel | Testes (15%) |
| T9 | Escrever os testes de interface e colocá-los no CI | Testes (15%) |
| T10 | Documentar a arquitetura e publicar o APK | App e documentação (10%) |
| T11 | Gravar o vídeo de 5 minutos | Comunicação |

> Exemplo de referência: `exemplos/tarefas-compose/` deste repositório, passos 11 a 16 do `PASSOS.md`. Projeto de referência: `github.com/fmarquesfilho/musi`, pastas `app/` e `shared/`.

---

## T1 — Quebrar o trabalho em cartões da sprint

**Objetivo.** Planejar a sprint no quadro e dar um dono a cada cartão.

**O que fazer.**
- [ ] Criar os cartões das tarefas T2 a T11 que se aplicam ao produto
- [ ] Atribuir um responsável a cada cartão e ligá-lo ao PR que o resolve
- [ ] Revisar o quadro no encontro de acompanhamento de cada semana

**Pronto quando.** O quadro mostra o que está na sprint, com dono, e cada cartão fechado aponta para um PR integrado.

**Referência.** [AVALIACAO.md](AVALIACAO.md#31-saúde-do-repositório).

---

## T2 — Levar o estado de cada tela para um ViewModel

**Objetivo.** O estado sobrevive à rotação e sai das funções `@Composable`.

**O que fazer.**
- [ ] Acrescentar o ViewModel multiplataforma (`org.jetbrains.androidx.lifecycle`, na mesma versão da Navigation)
- [ ] Para cada tela com dados: um estado imutável, um `MutableStateFlow` privado e um `StateFlow` público
- [ ] Transformar os eventos da tela em funções do ViewModel
- [ ] Na tela, ler o estado com `collectAsStateWithLifecycle()`; receber o ViewModel por parâmetro
- [ ] No desktop, acrescentar `kotlinx-coroutines-swing`

**Pronto quando.** Nenhuma tela com dados guarda estado de negócio em `remember`, e o estado sobrevive à rotação.

**Referência.** `moveis-s2.md`, capítulos 2 e 3 · exemplo, Passo 11.

---

## T3 — Modelar os estados da tela como classe selada

**Objetivo.** A tela mostra os quatro estados, e o compilador confere que nenhum foi esquecido.

**O que fazer.**
- [ ] Declarar a classe selada com `Carregando`, `Vazio`, `Sucesso` e `Erro`
- [ ] Carregar os dados numa corrotina do `viewModelScope`
- [ ] Na tela, um `when` sem `else`, com uma composição por estado
- [ ] Indicador de carregamento com descrição; mensagem para a lista vazia; mensagem e **Tentar de novo** para o erro

**Pronto quando.** Os quatro estados aparecem na tela, e um quinto estado faz o `when` deixar de compilar.

**Referência.** `moveis-s2.md`, capítulo 4 · exemplo, Passo 12.

---

## T4 — Separar as camadas e injetar as dependências

**Objetivo.** Domínio, dados e apresentação separados, com as dependências apontando para o domínio.

**O que fazer.**
- [ ] Criar os pacotes de domínio, dados e apresentação em `commonMain`
- [ ] Declarar os repositórios por interface no domínio
- [ ] Conferir que o domínio não importa Compose, Ktor, DataStore nem Koin
- [ ] Montar as peças num lugar só (Koin, como no exemplo, ou injeção manual); o ViewModel recebe o repositório pelo construtor
- [ ] Escrever um teste que monta o módulo

**Pronto quando.** As camadas estão em pacotes, os imports do domínio estão limpos, e o ViewModel não cria o repositório.

**Referência.** `moveis-s2.md`, capítulo 5 · exemplo, Passo 13.

---

## T5 — Buscar os dados no backend com o Ktor Client

**Objetivo.** Dados reais, do backend escolhido na Sprint 0.

**O que fazer.**
- [ ] Acrescentar o Ktor Client (core, content negotiation, JSON; OkHttp no Android; CIO ou outro no desktop)
- [ ] Escrever os DTOs no pacote de dados e a conversão para o domínio
- [ ] Escrever o repositório remoto, com `expectSuccess = true` e `ignoreUnknownKeys = true`
- [ ] Declarar o endereço do backend por plataforma; no Android, a permissão `INTERNET`
- [ ] Testar o repositório com o `MockEngine`

**Pronto quando.** O app mostra dados do backend, e o domínio não conhece os DTOs.

**Referência.** `moveis-s2.md`, capítulo 6 · exemplo, Passo 14.

---

## T6 — Tratar falta de conexão e resposta inválida

**Objetivo.** Cada falha de rede tem um estado de tela próprio.

**O que fazer.**
- [ ] Declarar as falhas com nome no domínio (no mínimo, sem conexão e resposta inválida)
- [ ] No repositório, traduzir as exceções do Ktor nessas falhas
- [ ] No ViewModel, uma mensagem por falha; quando salvar falhar, manter os dados na tela e mostrar um aviso
- [ ] Testar cada falha (repositório com `MockEngine`; ViewModel com dublê)

**Pronto quando.** Com o backend parado e com uma resposta 500, o app mostra mensagens diferentes, sem crash.

**Referência.** `moveis-s2.md`, capítulo 6.3 · exemplo, Passo 14.

---

## T7 — Guardar os dados no aparelho

**Objetivo.** O app abre sem rede mostrando o último conteúdo.

**O que fazer.**
- [ ] Escolher o mecanismo (DataStore, Room ou SQLDelight) e justificar no `docs/arquitetura.md`
- [ ] Guardar os dados a cada resposta do backend
- [ ] Sem conexão, devolver os dados guardados, marcados como desatualizados; a tela avisa
- [ ] Receber a pasta dos arquivos de cada plataforma
- [ ] Testar: com conexão guarda; sem conexão devolve; sem nada guardado, erro

**Pronto quando.** Abrir o app com rede, fechar, desligar a rede e abrir de novo mostra o último conteúdo.

**Referência.** `moveis-s2.md`, capítulo 7 · exemplo, Passo 15.

---

## T8 — Escrever os testes de ViewModel

**Objetivo.** A sequência de estados testada, sem rede e sem esperar.

**O que fazer.**
- [ ] Acrescentar `kotlinx-coroutines-test` e `turbine` em `commonTest`
- [ ] Escrever um dublê do repositório, com demora em tempo virtual e resposta configurável
- [ ] ≥ 5 testes com `runTest`, `StandardTestDispatcher(testScheduler)` como `Main` e `estado.test { awaitItem() }`: carregando → sucesso, vazio, cada falha, tentar de novo, um evento

**Pronto quando.** ≥ 5 testes de ViewModel passam em `./gradlew :composeApp:desktopTest`.

**Referência.** `moveis-s2.md`, capítulo 8 · exemplo, Passo 16.

---

## T9 — Escrever os testes de interface e colocá-los no CI

**Objetivo.** Uma suíte de interface que prova que as telas funcionam, verde no CI.

**O que fazer.**
- [ ] Configurar `commonTest` com `kotlin("test")` e `compose.ui:ui-test`
- [ ] ≥ 5 testes de interface: um por tela principal, inválido e válido do formulário, um de navegação, os estados de erro e de vazio
- [ ] Passar ao ViewModel um repositório de teste, e um `Dispatchers.Main` de teste
- [ ] Acrescentar `./gradlew :composeApp:desktopTest` ao workflow, precedido de `libgl1 libegl1 libfontconfig1`

**Pronto quando.** ≥ 5 testes de interface e ≥ 5 de ViewModel passam no CI, verde na branch principal.

**Referência.** `moveis-s1-pte2.md`, capítulos 5 e 6 · `moveis-s2.md`, capítulo 8.

---

## T10 — Documentar a arquitetura e publicar o APK

**Objetivo.** Qualquer pessoa entende e roda o app.

**O que fazer.**
- [ ] `docs/arquitetura.md`: diagrama das camadas, o caminho de um dado do backend até a tela, e a justificativa da forma de gerenciar o estado e da persistência
- [ ] README: pré-requisitos, como apontar para o backend, como rodar o app e os testes, link do vídeo
- [ ] Gerar o APK de debug e anexá-lo a uma release do GitHub
- [ ] Percorrer os fluxos do MVP, com e sem rede, procurando crash

**Pronto quando.** Uma pessoa de fora roda o app em menos de 15 minutos, e o APK está na release.

**Referência.** [RUBRICAS.md](RUBRICAS.md#sprint-2) *App e documentação*.

---

## T11 — Gravar o vídeo de 5 minutos

**Objetivo.** Mostrar o produto final funcionando e explicar as escolhas.

**O que fazer.**
- [ ] Seguir o roteiro do guia (dados reais e os estados · sem rede · a arquitetura · os testes e o CI)
- [ ] Garantir que **todos os integrantes falam**
- [ ] Publicar o vídeo e linkar no `README.md`
- [ ] Mostrar o andamento na *daily meeting* do grupo (23 ou 25/11, ou 18/11 em sala)

**Pronto quando.** O vídeo tem cerca de 5 min, cobre o roteiro, todos falam, e está acessível pelo link.

**Referência.** [SPRINT-2.md](SPRINT-2.md) *Estrutura do vídeo* · [AVALIACAO.md](AVALIACAO.md#4-componente-c--comunicação).
