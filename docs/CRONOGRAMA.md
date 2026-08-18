# Cronograma — DIM0524 Desenvolvimento de Sistemas para Dispositivos Móveis

**Período**: 17/08/2026 a 19/12/2026
**Horário**: Segundas e quartas, 14:50 às 16:30 (24T34)

As aulas de 10/08, 12/08 e 17/08 não foram realizadas. O curso inicia em 19/08.

---

## Legenda

| Símbolo | Significado |
|---------|-------------|
| 🟢 | Aula presencial |
| 🎤 | Apresentação dos grupos (presencial ou online, conforme a coorte) |
| 🔵 | Encontro online no Google Meet — aula ou apoio ao projeto |
| 🚀 | Entrega da sprint, sexta-feira às 23:59 |
| 📚 | Prova escrita, presencial, em laboratório |
| 🔴 | Feriado ou atividades suspensas |
| — | Sem encontro |

---

## Estrutura das sprints

Uma Sprint 0 de quatro semanas, três sprints de projeto e um bloco final. Cada sprint tem duas ou três aulas presenciais com o conteúdo, um ou dois encontros online no horário da aula, e dois dias de apresentação na última semana — uma sessão online e uma em sala.

Todos os grupos apresentam em todas as sprints, exceto na Sprint 0. A entrega vence na sexta-feira que encerra a sprint. As regras de nota estão em [AVALIACAO.md](AVALIACAO.md).

---

## Visão geral

| Bloco | Período | Tema | Apresentações | Entrega |
|-------|---------|------|---------------|---------|
| Sprint 0 | 17/08 a 11/09 | Kotlin, Compose e a primeira tela | — | 11/09 |
| Sprint 1 | 14/09 a 02/10 | Interface e navegação | 28 e 30/09 | 02/10 |
| Sprint 2 | 05/10 a 23/10 | Estado e arquitetura | 14 e 19/10 | 23/10 |
| Sprint 3 | 26/10 a 20/11 | Dados, rede e offline-first | 16 e 18/11 | 20/11 |
| Bloco final | 23/11 a 11/12 | Recursos do dispositivo, segurança e distribuição | 07 e 09/12 | 11/12 |
| Prova escrita | 21/10 | Sprints 0 a 2 | — | — |
| Prova de reposição | 30/11 | Sprints 0 a 3, cumulativa e opcional | — | — |

---

## Feriados e suspensões

| Data | Dia | Evento |
|------|-----|--------|
| 07/09 | Segunda | Independência do Brasil |
| 12/10 | Segunda | Nossa Senhora Aparecida |
| 28/10 | Quarta | Dia do Servidor Público |
| 02/11 | Segunda | Finados |

Fonte: Calendário Universitário UFRN 2026, Resolução nº 074/2025-CONSAD.

---

## Apresentações

| Sprint | Coorte B — online | Coorte A — presencial |
|--------|-------------------|------------------------|
| Sprint 1 | 28/09 | 30/09 |
| Sprint 2 | 19/10 | 14/10 |
| Sprint 3 | 16/11 | 18/11 |
| Entrega final | 07/12 | 09/12 |

Na Sprint 2 a ordem se inverte por causa do feriado de 12/10 e da prova de 21/10. A escolha de coorte e as regras de apresentação estão em [AVALIACAO.md](AVALIACAO.md#4-componente-c--comunicação).

---

## Sprint 0 — Kotlin, Compose e a primeira tela

**17/08 a 11/09. Entrega: 11/09 (sexta), 23:59.**

| Data | Dia | Tipo | Atividade |
|------|-----|------|-----------|
| 17/08 | Seg | — | Não houve aula |
| 19/08 | Qua | 🟢 | Apresentação do curso e dos critérios de avaliação. Panorama do desenvolvimento móvel: plataformas nativas e multiplataforma |
| 24/08 | Seg | 🟢 | Kotlin Multiplatform e Compose Multiplatform: o que são e o que muda em relação ao nativo. Kotlin: tipos, `val` e `var`, null safety, coleções, funções, data classes, classes seladas e `when` como expressão. Ambientes de desenvolvimento sem instalação. Formação de grupos, escolha de coorte, plataforma-alvo e estratégia de backend |
| 26/08 | Qua | 🔵 | Encontro online — dúvidas sobre a proposta e o ambiente |
| 31/08 | Seg | 🟢 | Compose: funções `@Composable`, recomposição, estado com `remember` e `mutableStateOf`, elevação de estado. Modificadores. Layouts com `Column`, `Row` e `Box`. Compose Hot Reload e previews |
| 02/09 | Qua | 🟢 | Componentes próprios e reutilizáveis. Tema Material 3: esquema de cor e tipografia. Funções de extensão e funções de escopo. Estrutura de um projeto KMP, Gradle e catálogo de versões. `kdoctor`. Primeiro workflow de CI com `ktlint` e `detekt` |
| 07/09 | Seg | 🔴 | Independência do Brasil |
| 09/09 | Qua | 🔵 | Encontro online — dúvidas sobre o ambiente e a proposta |

O que entregar e como é avaliado: [RUBRICAS.md](RUBRICAS.md#sprint-0). Guia com templates e exemplos: [SPRINT-0.md](SPRINT-0.md).

---

## Sprint 1 — Interface e navegação

**14/09 a 02/10. Entrega: 02/10 (sexta), 23:59.**

| Data | Dia | Tipo | Atividade |
|------|-----|------|-----------|
| 14/09 | Seg | 🟢 | Listas com `LazyColumn` e `LazyRow`, chaves e desempenho de rolagem. Formulários e validação. Material 3 em profundidade: modo claro e escuro, e esquema de cor derivado |
| 16/09 | Qua | 🟢 | Responsividade e adaptatividade com classes de tamanho de janela. Acessibilidade. Navegação com Navigation Compose: rotas tipadas, argumentos, pilha de retorno e deep links |
| 21/09 | Seg | 🔵 | Encontro online — dúvidas sobre o projeto |
| 23/09 | Qua | 🟢 | Testes em Compose Multiplatform: teste de unidade com `kotlin.test` e teste de interface — encontrar, interagir e verificar. Oficina de escrita de testes sobre o projeto |
| 28/09 | Seg | 🎤 | Apresentações da Coorte B, online |
| 30/09 | Qua | 🎤 | Apresentações da Coorte A, em sala de aula |

O que entregar e como é avaliado: [RUBRICAS.md](RUBRICAS.md#sprint-1).

---

## Sprint 2 — Estado e arquitetura

**05/10 a 23/10. Entrega: 23/10 (sexta), 23:59.**

A prova escrita ocorre dentro desta sprint, em 21/10. O conteúdo fica concentrado em 05 e 07/10, e as apresentações se antecipam para 14 e 19/10.

| Data | Dia | Tipo | Atividade |
|------|-----|------|-----------|
| 05/10 | Seg | 🟢 | Kotlin assíncrono: `suspend`, coroutines e concorrência estruturada. `Flow` e `StateFlow`. ViewModel multiplataforma e coleta consciente do ciclo de vida |
| 07/10 | Qua | 🔵 | Aula online — modelagem do estado de tela com classes seladas. Arquitetura em camadas de apresentação, domínio e dados. Injeção de dependências com Koin. Testes de ViewModel com Turbine |
| 12/10 | Seg | 🔴 | Nossa Senhora Aparecida |
| 14/10 | Qua | 🎤 | Apresentações da Coorte A, em sala de aula |
| 19/10 | Seg | 🎤 | Apresentações da Coorte B, online |
| 21/10 | Qua | 📚 | **Prova escrita** — presencial, em laboratório. Conteúdo das Sprints 0 a 2 |

O que entregar e como é avaliado: [RUBRICAS.md](RUBRICAS.md#sprint-2).

---

## Sprint 3 — Dados, rede e offline-first

**26/10 a 20/11. Entrega: 20/11 (sexta), 23:59.**

Esta sprint tem quatro semanas por causa dos feriados de 28/10 e 02/11.

| Data | Dia | Tipo | Atividade |
|------|-----|------|-----------|
| 26/10 | Seg | — | Sem encontro |
| 28/10 | Qua | 🔴 | Dia do Servidor Público |
| 02/11 | Seg | 🔴 | Finados |
| 04/11 | Qua | 🔵 | Aula online — Ktor Client: requisições, timeouts, retentativa com backoff e interceptação. Serialização com kotlinx.serialization e mapeamento entre DTOs e entidades |
| 09/11 | Seg | 🟢 | Persistência local com Room ou SQLDelight, e chave-valor com DataStore. Os quatro estados de interface e o tratamento de erro |
| 11/11 | Qua | 🟢 | Estratégia offline-first: cache, fila de operações pendentes, sincronização e resolução de conflitos. Autenticação no cliente e armazenamento de token |
| 16/11 | Seg | 🎤 | Apresentações da Coorte B, online |
| 18/11 | Qua | 🎤 | Apresentações da Coorte A, em sala de aula |

O que entregar e como é avaliado: [RUBRICAS.md](RUBRICAS.md#sprint-3).

---

## Bloco final — Recursos do dispositivo, segurança e distribuição

**23/11 a 11/12. Entrega final: 11/12 (sexta), 23:59.**

O bloco final não tem entrega própria: o conteúdo apresentado aqui é avaliado na entrega final do projeto e na apresentação.

| Data | Dia | Tipo | Atividade |
|------|-----|------|-----------|
| 23/11 | Seg | 🟢 | Recursos do dispositivo com `expect`/`actual`: câmera, galeria, geolocalização, sensores, compartilhamento e notificações. Modelo de permissões de Android e iOS, solicitação em contexto e negação permanente |
| 25/11 | Qua | 🔵 | Aula online — armazenamento seguro, OWASP Mobile Top 10, ofuscação, certificate pinning e proteção de chaves. Interoperabilidade entre Kotlin e Swift |
| 30/11 | Seg | 📚 | **Prova de reposição** — presencial, em laboratório. Cumulativa, Sprints 0 a 3. Opcional |
| 02/12 | Qua | 🔵 | Aula online — build, assinatura e canais de distribuição. Pipeline de CI/CD com GitHub Actions. Desempenho e observabilidade em produção |
| 07/12 | Seg | 🎤 | Apresentações finais da Coorte B, online |
| 09/12 | Qua | 🎤 | Apresentações finais da Coorte A, em sala de aula |
| 11/12 | Sex | 🚀 | **Entrega final**, 23:59 |
| 14/12 e 16/12 | Seg e Qua | — | Sem encontro. Divulgação das notas e do retorno escrito no SIGAA |

O que entregar e como é avaliado: [RUBRICAS.md](RUBRICAS.md#entrega-final).

