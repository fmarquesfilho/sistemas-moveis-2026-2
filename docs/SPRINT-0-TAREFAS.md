# Tarefas da Sprint 0 — DIM0524

Estes são os enunciados das tarefas da Sprint 0, prontos para virar cartões no GitHub
Projects. Cada um tem um objetivo, o que fazer, e o *pronto quando* alinhado à rubrica.

O **como** (templates, exemplos e a estrutura da proposta e do vídeo) está em
[SPRINT-0.md](SPRINT-0.md) — as tarefas apontam para a seção certa em vez de repeti-la. Os
pesos vêm de [RUBRICAS.md](RUBRICAS.md#sprint-0). As opções de plataforma, interface e
backend estão em [STACK.md](STACK.md). Prazo em [CRONOGRAMA.md](CRONOGRAMA.md#visão-geral).

| # | Tarefa | Critério da rubrica |
|---|---|---|
| T1 | Gerar o esqueleto KMP e criar o repositório | Projeto funcional e CI (30%) |
| T2 | Escrever a visão do produto | Proposta do produto (25%) |
| T3 | Definir o MVP | Proposta do produto (25%) |
| T4 | Escolher e justificar plataforma-alvo e backend | Justificativa de plataforma e backend (30%) |
| T5 | Montar o backlog no GitHub Projects | Proposta do produto (25%) |
| T6 | Construir a primeira tela em Compose | Primeira tela (15%) |
| T7 | Deixar o CI verde (passando) com `ktlint` e `detekt` | Projeto funcional e CI (30%) |
| T8 | Consolidar `docs/proposta.md` | Todos |
| T9 | Gravar o vídeo de 5 minutos | Todos |

> Projeto de referência: `github.com/fmarquesfilho/musi`, pasta `app/` — tem a tela em
> Compose com componente próprio e estado elevado, que é o alvo de T6.

---

## T1 — Gerar o esqueleto KMP e criar o repositório

**Objetivo.** Ter um projeto KMP que compila, no repositório da equipe.

**O que fazer.**
- [ ] Gerar o esqueleto em `kmp.jetbrains.com` com os alvos **Android**, **iOS** e **Desktop**, compartilhando a interface com Compose Multiplatform
- [ ] Rodar o `kdoctor` e resolver o que ele apontar
- [ ] Criar o repositório **público**, com `README.md` (equipe, matrículas, coorte)
- [ ] Confirmar que o app compila e roda nos alvos Android e desktop

**Pronto quando.** O projeto compila e roda em Android e desktop, e o repositório é público com o README.

**Referência.** [SPRINT-0.md](SPRINT-0.md) *Como começar o projeto* · [RUBRICAS.md](RUBRICAS.md#sprint-0) *Projeto funcional e CI*.

---

## T2 — Escrever a visão do produto

**Objetivo.** Deixar claro por que o produto existe e que problema resolve.

**O que fazer.**
- [ ] Preencher o template de visão (seção *Visão do produto* do guia)
- [ ] Conferir: usuário definido, problema específico, valor único, viável em um semestre
- [ ] Registrar na seção 1 de `docs/proposta.md`

**Pronto quando.** A visão nomeia um público e um problema delimitado, não genérico.

**Referência.** [SPRINT-0.md](SPRINT-0.md) *Visão do produto* · [RUBRICAS.md](RUBRICAS.md#sprint-0) *Proposta do produto*.

---

## T3 — Definir o MVP

**Objetivo.** Delimitar o escopo mínimo que entrega valor, protegendo o prazo.

**O que fazer.**
- [ ] Listar o que está **no MVP** e, explicitamente, o que fica **fora**
- [ ] Enunciar a hipótese de valor: *acreditamos que [usuários] vão [comportamento] porque [benefício]*
- [ ] Registrar na seção 2 de `docs/proposta.md`

**Pronto quando.** O MVP é viável em quatro sprints e tem o fora-de-escopo declarado.

**Referência.** [SPRINT-0.md](SPRINT-0.md) *Definição do MVP*.

---

## T4 — Escolher e justificar plataforma-alvo e backend

**Objetivo.** Tomar as duas decisões técnicas centrais, a partir do produto.

**O que fazer.**
- [ ] Escolher a plataforma-alvo: **Android ou iOS**
- [ ] Escolher a estratégia de backend (ver [STACK.md](STACK.md#4-escolha-do-backend))
- [ ] Justificar as duas escolhas pelas características do produto e do público, com as alternativas descartadas e a razão
- [ ] Registrar nas seções 4 e 5 de `docs/proposta.md`

**Pronto quando.** As duas escolhas estão justificadas a partir do produto, com alternativas consideradas — não por conveniência.

**Referência.** [SPRINT-0.md](SPRINT-0.md) *Estrutura de `docs/proposta.md`* · [RUBRICAS.md](RUBRICAS.md#sprint-0) *Justificativa de plataforma e backend*.

---

## T5 — Montar o backlog no GitHub Projects

**Objetivo.** Transformar o MVP em histórias priorizadas e estimadas.

**O que fazer.**
- [ ] Criar no mínimo 5 itens no formato *como [papel], quero [ação] para [benefício]*
- [ ] Escrever critérios de aceitação para as histórias do topo
- [ ] Estimar ao menos 3 e priorizar todas (P1/P2/P3)

**Pronto quando.** Há ≥ 5 histórias, ≥ 3 estimadas, todas priorizadas, no quadro do GitHub Projects.

**Referência.** [SPRINT-0.md](SPRINT-0.md) *Backlog inicial*.

---

## T6 — Construir a primeira tela em Compose

**Objetivo.** Entregar uma tela real, com um componente próprio e estado elevado.

**O que fazer.**
- [ ] Escrever ao menos um componente próprio e reutilizável (ex.: um cartão)
- [ ] Manter o estado elevado: a tela recebe o que mostra e devolve eventos, sem guardar estado interno
- [ ] Seguir as convenções de código Kotlin
- [ ] Adicionar ao menos um `@Preview` (ex.: lista cheia e vazia)

**Pronto quando.** A tela tem um componente próprio reutilizável e o estado é elevado corretamente — não tudo num único `@Composable`, nem só o gerado pelo assistente.

**Referência.** [RUBRICAS.md](RUBRICAS.md#sprint-0) *Primeira tela*. Exemplo: `app/` do MUSI.

---

## T7 — Deixar o CI verde (passando) com `ktlint` e `detekt`

**Objetivo.** Ter um pipeline que garante compilação e estilo a cada push.

**O que fazer.**
- [ ] Criar o workflow do GitHub Actions que compila o projeto
- [ ] Rodar `ktlintCheck` e `detekt`, e deixá-los limpos
- [ ] Garantir que fica verde (passando) no GitHub Actions

**Pronto quando.** O CI compila o app e passa `ktlintCheck` e `detekt` sem avisos, verde no GitHub Actions. Ainda não é necessário publicar o app em loja.

**Referência.** [RUBRICAS.md](RUBRICAS.md#sprint-0) *Projeto funcional e CI*.

---

## T8 — Consolidar `docs/proposta.md`

**Objetivo.** Reunir tudo num documento de no máximo 5 páginas.

**O que fazer.**
- [ ] Montar as 7 seções na ordem do guia (visão, MVP, link do backlog, plataforma-alvo, backend, equipe, coorte/integração)
- [ ] Garantir que a justificativa técnica parte das características do produto

**Pronto quando.** `docs/proposta.md` tem as 7 seções, cabe em 5 páginas e aponta para o backlog.

**Referência.** [SPRINT-0.md](SPRINT-0.md) *Estrutura de `docs/proposta.md`*.

---

## T9 — Gravar o vídeo de 5 minutos

**Objetivo.** Apresentar a equipe, o produto e as escolhas técnicas.

**O que fazer.**
- [ ] Seguir o roteiro do guia (equipe · visão · MVP · plataforma-alvo e backend, com justificativa · backlog)
- [ ] Garantir que **todos os integrantes falam**
- [ ] Publicar o vídeo e linkar no `README.md` ou na proposta

**Pronto quando.** O vídeo tem ~5 min, cobre o roteiro, todos falam, e está acessível pelo link.

**Referência.** [SPRINT-0.md](SPRINT-0.md) *Estrutura do vídeo*.
