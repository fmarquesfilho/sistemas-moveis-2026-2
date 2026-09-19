# Tarefas da Sprint 1 — DIM0524

Estes são os enunciados das tarefas da Sprint 1, prontos para virar cartões no GitHub
Projects. Cada um tem um objetivo, o que fazer, e o *pronto quando* alinhado à rubrica.

O **como** está em [SPRINT-1.md](SPRINT-1.md) e nas leituras da sprint
([`leituras/moveis-s1-pte1.md`](../leituras/moveis-s1-pte1.md) e
[`leituras/moveis-s1-pte2.md`](../leituras/moveis-s1-pte2.md)) — as tarefas apontam para a
seção certa em vez de repeti-la. Os pesos vêm de [RUBRICAS.md](RUBRICAS.md#sprint-1). Prazo
em [CRONOGRAMA.md](CRONOGRAMA.md#visão-geral): **02/10, 23:59**.

| # | Tarefa | Critério da rubrica |
|---|---|---|
| T1 | Quebrar as histórias P1 em cartões da sprint | Atividade no repositório |
| T2 | Construir as telas do MVP com componentes próprios | Telas do MVP (25%) |
| T3 | Validar os formulários | Telas do MVP (25%) · Testes de interface (15%) |
| T4 | Montar o grafo de navegação com rotas tipadas | Navegação (25%) |
| T5 | Declarar e demonstrar um deep link | Navegação (25%) |
| T6 | Aplicar o tema claro e escuro | Tema, responsividade e adaptatividade (20%) |
| T7 | Adaptar o layout a duas larguras de janela | Tema, responsividade e adaptatividade (20%) |
| T8 | Revisar a acessibilidade e testar com o leitor de tela | Acessibilidade (15%) |
| T9 | Escrever os testes de interface e colocá-los no CI | Testes de interface (15%) |
| T10 | Gravar o vídeo de 5 minutos e preparar a apresentação | Comunicação |

> Exemplo de referência: `exemplos/tarefas-compose/` deste repositório, passos 6 a 9 do
> `PASSOS.md`. Projeto de referência: `github.com/fmarquesfilho/musi`, pasta `app/`.

---

## T1 — Quebrar as histórias P1 em cartões da sprint

**Objetivo.** Levar para a sprint só o que cabe em duas semanas e dar um dono a cada cartão.

**O que fazer.**
- [ ] Mover para a coluna da sprint as histórias P1 que viram telas
- [ ] Criar os cartões das tarefas T2 a T10 que se aplicam ao produto
- [ ] Atribuir um responsável a cada cartão e ligá-lo ao PR que o resolve

**Pronto quando.** O quadro mostra o que está na sprint, com dono, e cada cartão fechado aponta para um PR integrado.

**Referência.** [AVALIACAO.md](AVALIACAO.md#31-saúde-do-repositório) *Itens movimentados no quadro e vinculados a PRs*.

---

## T2 — Construir as telas do MVP com componentes próprios

**Objetivo.** Ter as telas principais do produto funcionando, com código reaproveitável.

**O que fazer.**
- [ ] Implementar uma tela por história P1, em `commonMain`
- [ ] Extrair componentes próprios para o que se repete (cartão, linha, campo)
- [ ] Manter o estado elevado: telas recebem dados e devolvem eventos
- [ ] Escrever `@Preview` de cada tela, com dados fixos, incluindo o estado vazio

**Pronto quando.** Todas as telas principais existem, sem repetição de código entre elas, e cada uma tem preview.

**Referência.** [SPRINT-1.md](SPRINT-1.md) *Telas do MVP* · `moveis-s1-pte1.md`, capítulos 4 a 7.

---

## T3 — Validar os formulários

**Objetivo.** Impedir entrada inválida e dizer ao usuário o que corrigir.

**O que fazer.**
- [ ] Derivar a validade do estado do formulário (função pura, sem `remember` extra)
- [ ] Desabilitar a ação ou mostrar a mensagem de erro enquanto a entrada for inválida
- [ ] Escolher o teclado certo para cada campo (número, e-mail, texto)

**Pronto quando.** Nenhum formulário aceita entrada inválida, e o motivo aparece na tela.

**Referência.** `moveis-s1-pte1.md`, capítulo 8.

---

## T4 — Montar o grafo de navegação com rotas tipadas

**Objetivo.** Trocar de tela por um grafo, com pilha de retorno e argumentos tipados.

**O que fazer.**
- [ ] Adicionar `navigation-compose` e o plugin de serialização
- [ ] Declarar cada destino como tipo `@Serializable`, com os argumentos como propriedades
- [ ] Montar o `NavHost`; as telas recebem lambdas, não o `NavController`
- [ ] Conferir que o Voltar do sistema desempilha e que o estado aparece atualizado ao voltar
- [ ] Aninhar grafos se houver um fluxo de várias telas

**Pronto quando.** Todas as telas estão no grafo, ao menos uma rota tem argumento, e o Voltar funciona.

**Referência.** [SPRINT-1.md](SPRINT-1.md) *Navegação* · `moveis-s1-pte2.md`, capítulo 4.

---

## T5 — Declarar e demonstrar um deep link

**Objetivo.** Abrir o app direto num destino a partir de um endereço externo.

**O que fazer.**
- [ ] Declarar `navDeepLink<Rota>(basePath = ...)` no destino
- [ ] Declarar o `intent-filter` no `AndroidManifest.xml` (ou o URL scheme no iOS)
- [ ] Testar com `adb shell am start -a android.intent.action.VIEW -d "..."` e conferir o Voltar
- [ ] Registrar o comando ou o link no `README.md`

**Pronto quando.** O deep link abre o destino com o argumento certo, e isso aparece no vídeo.

**Referência.** `moveis-s1-pte2.md`, seção 4.5.

---

## T6 — Aplicar o tema claro e escuro

**Objetivo.** Ter um tema Material 3 coerente nos dois modos.

**O que fazer.**
- [ ] Definir o esquema de cor (claro e escuro) e usar as cores pelos papéis
- [ ] Pôr uma `Surface` ou `Scaffold` na raiz
- [ ] Conferir cada tela nos dois modos

**Pronto quando.** Todas as telas ficam legíveis e coerentes nos modos claro e escuro, sem cor fixa à mão.

**Referência.** `moveis-s1-pte1.md`, capítulo 9 · `moveis-s1-pte2.md`, seção 2.5.

---

## T7 — Adaptar o layout a duas larguras de janela

**Objetivo.** Aproveitar o espaço em janelas largas sem quebrar as estreitas.

**O que fazer.**
- [ ] Ler a classe de largura com `currentWindowAdaptiveInfo().windowSizeClass`
- [ ] Definir o layout de cada largura (por exemplo, lista e detalhe lado a lado a partir de 600 dp)
- [ ] Separar a decisão de layout do conteúdo, para testar os dois modos
- [ ] Conferir: celular em pé, celular deitado ou tablet, janela desktop redimensionada

**Pronto quando.** O app muda de layout em ≥ 2 larguras e nenhuma tela tem conteúdo cortado ou vazando.

**Referência.** [SPRINT-1.md](SPRINT-1.md) *Tema, responsividade e adaptatividade* · `moveis-s1-pte2.md`, capítulo 2.

---

## T8 — Revisar a acessibilidade e testar com o leitor de tela

**Objetivo.** Tornar o app usável por quem usa leitor de tela ou tem pouca precisão ao tocar.

**O que fazer.**
- [ ] Dar `contentDescription` a todo elemento interativo sem texto visível
- [ ] Juntar em um elemento as linhas que alternam algo (`toggleable` com `role`)
- [ ] Marcar os títulos de tela com `heading()`
- [ ] Conferir alvos de toque ≥ 48 dp e contraste (Accessibility Scanner)
- [ ] Percorrer as telas com o TalkBack e registrar no `README.md` o que foi ajustado

**Pronto quando.** Cada elemento é anunciado com um nome que faz sentido, toda ação é alcançável pelo leitor de tela, e contraste e alvos foram verificados.

**Referência.** [SPRINT-1.md](SPRINT-1.md) *Acessibilidade* · `moveis-s1-pte2.md`, capítulo 3.

---

## T9 — Escrever os testes de interface e colocá-los no CI

**Objetivo.** Ter uma suíte que prove que as telas funcionam e continua verde.

**O que fazer.**
- [ ] Configurar `commonTest` com `kotlin("test")` e `compose.ui:ui-test`, e o `desktopTest` com `compose.desktop.currentOs`
- [ ] Escrever ≥ 5 testes de interface: um por tela principal, inválido e válido do formulário, um de navegação
- [ ] Testar as regras puras com `kotlin.test`
- [ ] Acrescentar `./gradlew :composeApp:desktopTest` ao workflow da Sprint 0, precedido da instalação de `libgl1 libegl1 libfontconfig1` (ver o guia)

**Pronto quando.** Há ≥ 5 testes de interface cobrindo as telas principais e o formulário, e o CI roda os testes e fica verde na branch principal.

**Referência.** [SPRINT-1.md](SPRINT-1.md) *Testes de interface* · `moveis-s1-pte2.md`, capítulos 5 e 6.

---

## T10 — Gravar o vídeo de 5 minutos e preparar a apresentação

**Objetivo.** Mostrar o incremento funcionando e explicar as escolhas.

**O que fazer.**
- [ ] Seguir o roteiro do guia (telas · navegação · deep link e larguras · acessibilidade · testes e CI)
- [ ] Garantir que **todos os integrantes falam**
- [ ] Publicar o vídeo e linkar no `README.md`
- [ ] Ensaiar a apresentação da coorte (28/09 online ou 30/09 em sala)

**Pronto quando.** O vídeo tem ~5 min, cobre o roteiro, todos falam, e está acessível pelo link.

**Referência.** [SPRINT-1.md](SPRINT-1.md) *Estrutura do vídeo* · [AVALIACAO.md](AVALIACAO.md#4-componente-c--comunicação).
