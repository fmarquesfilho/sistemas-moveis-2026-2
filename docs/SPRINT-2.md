# Guia da Sprint 2 — DIM0524

Prazo em [CRONOGRAMA.md](CRONOGRAMA.md#visão-geral): entrega final em **30/11 (segunda), 23:59**. Conteúdo na aula presencial de 26/10 e em vídeo, publicado no SIGAA até 30/10 (as aulas de 19 e 21/10 foram canceladas); revisão para a prova em 04/11 (presencial) e 09/11 (online); acompanhamento online em 16/11; *daily meetings* online em 23 e 25/11 (presencial em 18/11, para quem avisar antes). O que entregar e como é avaliado: [RUBRICAS.md](RUBRICAS.md#sprint-2). Os enunciados, prontos para virar cartões no quadro, estão em [SPRINT-2-TAREFAS.md](SPRINT-2-TAREFAS.md).

A Sprint 2 é a última do semestre: o que for entregue em 30/11 é o produto final. O app passa a ter o **estado num ViewModel**, as **camadas separadas**, **dados reais** vindos do backend escolhido na Sprint 0, uma **cópia local** para abrir sem rede, e os **testes** (de interface, que saíram da Sprint 1, e de ViewModel).

---

## O que entregar

| Critério da rubrica | Peso | Em uma frase |
|---|---|---|
| Estado e arquitetura | 25% | ViewModel com `StateFlow`; `data`, `domain` e `presentation` separados em `commonMain`; domínio sem Compose nem infraestrutura; repositórios por interface |
| Estados da interface | 15% | Carregando, erro, vazio e sucesso, por classe selada, refletidos na tela |
| Dados reais e erro de rede | 25% | Dados do backend com Ktor Client e kotlinx.serialization, DTO separado; falha de conexão e resposta inválida, cada uma com estado próprio |
| Persistência local | 10% | Os dados sobrevivem ao fechamento do app, e o app abre sem rede mostrando o último conteúdo |
| Testes | 15% | ≥ 5 testes de interface e ≥ 5 de ViewModel com Turbine e dublês, verdes no CI |
| App e documentação | 10% | Sem crash; `docs/arquitetura.md`; README em menos de 15 min; APK de debug numa release |

Além da entrega técnica, a nota da sprint tem a atividade no repositório (30%) e a comunicação (20%): ver [AVALIACAO.md](AVALIACAO.md#2-nota-de-cada-sprint).

---

## Material de apoio

- Leitura da sprint: [`leituras/moveis-s2.md`](../leituras/moveis-s2.md) — corrotinas, Flow e ViewModel (26/10); camadas, Koin, Ktor Client, cache e testes (vídeo).
- Slides: `slides/sistemas-moveis-slides-06.md` (26/10) e `slides/sistemas-moveis-slides-07.md` (vídeo).
- Exemplo: `exemplos/tarefas-compose/`, passos 11 a 16 do `PASSOS.md`. No branch `aula`, as tags `passo-11` a `passo-16` guardam cada passo. O exemplo consome a API de Tarefas de Web II (`web2-2026-2/exemplos`, `docker compose up --build`); sem ela, roda com `TAREFAS_API=memoria`.
- Os testes de interface da Sprint 1 (agora avaliados aqui): capítulos 5 e 6 da leitura `moveis-s1-pte2.md` e Passo 9 do exemplo.
- Kotlin: a leitura `moveis-s1-kotlin.md`, sobre lambdas, extensões e lambdas com receptor.
- Ambiente sem instalação: o Codespace deste repositório (Android Studio e o app com Hot Reload no navegador).

---

## Estado e arquitetura

- Cada tela com dados tem um ViewModel multiplataforma que expõe um `StateFlow` de um estado imutável (`data class`). O `MutableStateFlow` fica privado.
- A tela lê o estado com `collectAsStateWithLifecycle()` e repassa eventos chamando funções do ViewModel. Nenhuma regra de negócio dentro de `@Composable`.
- Pacotes `domain`, `data` e `presentation` (ou equivalentes em português) em `commonMain`. O domínio não importa Compose, Ktor, DataStore nem Koin.
- Repositórios declarados por interface no domínio e implementados em `data`. O ViewModel recebe o repositório pelo construtor (Koin ou injeção manual), e não o cria.

---

## Estados da interface

- Uma classe selada com, no mínimo, `Carregando`, `Vazio`, `Sucesso` e `Erro`.
- Na tela, um `when` sobre o estado, **sem `else`**: um estado novo deixa de compilar até ser tratado.
- Cada estado tem uma aparência: indicador com descrição para o leitor de tela; mensagem e um convite para a lista vazia; mensagem e um botão de tentar de novo para o erro.

---

## Dados reais e erro de rede

- Os dados vêm do backend escolhido na Sprint 0, com o Ktor Client e o kotlinx.serialization.
- DTOs no pacote `data`, separados das entidades do domínio, com uma conversão explícita.
- `expectSuccess = true` (4xx e 5xx viram exceção) e `ignoreUnknownKeys = true` (campos novos não quebram o app).
- As exceções da biblioteca viram falhas do domínio com nome. No mínimo, **falta de conexão** e **resposta inválida** (status de erro ou JSON fora do formato), cada uma com estado de tela próprio.
- O endereço do backend por plataforma (`expect`/`actual`); no Android, a permissão `INTERNET`.

---

## Persistência local

- Os dados do usuário sobrevivem ao fechamento do app: DataStore (como no exemplo), Room ou SQLDelight.
- Sem rede, o app abre mostrando o último conteúdo recebido, e avisa que pode estar desatualizado.
- A pasta dos arquivos vem de cada plataforma (`filesDir` no Android).

---

## Testes

- **≥ 5 testes de ViewModel** com Turbine e dublês do repositório: a sequência de estados (carregando → sucesso; carregando → erro), o tentar de novo, os eventos. Com `runTest` e `Dispatchers.setMain(StandardTestDispatcher(testScheduler))`.
- **≥ 5 testes de interface** (`runComposeUiTest`): um por tela principal, os casos inválido e válido do formulário, um de navegação, e os estados de erro e vazio. Encontrem os elementos pelo texto ou pela descrição, como o usuário.
- Todos no alvo desktop (`./gradlew :composeApp:desktopTest`), no CI. Num runner Linux, instalem antes `libgl1 libegl1 libfontconfig1`.

---

## App e documentação

- Os fluxos do MVP rodam sem crash na plataforma-alvo, inclusive sem rede.
- `docs/arquitetura.md`: um diagrama das camadas e a justificativa da forma de gerenciar o estado.
- README que permite rodar em menos de 15 minutos: pré-requisitos, como apontar para o backend, como rodar os testes.
- O APK de debug anexado a uma release do GitHub (`./gradlew :composeApp:assembleDebug`).

---

## Estrutura do vídeo — 5 minutos

| Tempo | Conteúdo |
|---|---|
| 30 s | O que a sprint entregou |
| 1 min 30 s | O app com dados reais: carregando, sucesso, erro com tentar de novo e a lista vazia |
| 1 min | Sem rede: o app abre mostrando o último conteúdo |
| 1 min | A arquitetura: o diagrama, o ViewModel e o repositório |
| 1 min | Os testes rodando e o CI verde |

Todos os integrantes devem falar. Link no `README.md`.

---

## Como o grupo é avaliado nesta sprint

- **Entrega técnica (50%)**: a rubrica da Sprint 2, sobre o estado da branch principal no prazo (hash do último commit).
- **Atividade no repositório (30%)**: CI verde, commits distribuídos pelas semanas, ao menos um PR integrado por integrante, PRs revisados por outro integrante e cartões do quadro ligados a PRs. O Fator de Participação individual segue [AVALIACAO.md](AVALIACAO.md#32-fator-de-participação).
- **Comunicação (20%)**: média entre o vídeo e a *daily meeting* (23 ou 25/11, online, ou 18/11, em sala).

O registro de uso de IA continua em `docs/uso-de-ia.md`.
