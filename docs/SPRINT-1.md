# Guia da Sprint 1 — DIM0524

Prazo em [CRONOGRAMA.md](CRONOGRAMA.md#visão-geral): entrega em **02/10 (sexta), 23:59**, com apresentações em 28/09 (Coorte B, online) e 30/09 (Coorte A, em sala). O que entregar e como é avaliado: [RUBRICAS.md](RUBRICAS.md#sprint-1). Os enunciados, prontos para virar cartões no quadro, estão em [SPRINT-1-TAREFAS.md](SPRINT-1-TAREFAS.md).

A Sprint 1 é a sprint da **interface e da navegação**: as telas do MVP ficam de pé, ligadas por um grafo de navegação, adaptadas a mais de uma largura de janela, acessíveis e cobertas por testes de interface.

---

## O que entregar

| Critério da rubrica | Peso | Em uma frase |
|---|---|---|
| Telas do MVP | 25% | As telas principais implementadas, com componentes próprios reutilizáveis |
| Navegação | 25% | Navigation Compose com rotas tipadas, argumentos e ao menos um deep link demonstrado |
| Tema, responsividade e adaptatividade | 20% | Material 3 claro e escuro, layout adaptado a ≥ 2 larguras, sem overflow |
| Acessibilidade | 15% | Descrições de conteúdo, contraste verificado, alvos ≥ 48 dp, leitor de tela testado |
| Testes de interface | 15% | ≥ 5 testes cobrindo as telas principais e a validação do formulário, verdes no CI |

Além da entrega técnica, a nota da sprint tem a atividade no repositório (30%) e a comunicação (20%): ver [AVALIACAO.md](AVALIACAO.md#2-nota-de-cada-sprint).

---

## Material de apoio

- Leituras da sprint: [`leituras/moveis-s1-pte1.md`](../leituras/moveis-s1-pte1.md) (listas, formulários e Material 3, 14/09) e [`leituras/moveis-s1-pte2.md`](../leituras/moveis-s1-pte2.md) (responsividade, acessibilidade, navegação e testes, 21/09).
- Exemplo: `exemplos/tarefas-compose/`, passos 1 a 9 do `PASSOS.md`. Tem tudo o que a rubrica pede em escala pequena: duas telas, rotas tipadas com argumento, deep link, lista e detalhe lado a lado em janela larga, linha acessível e 8 testes.
- Ambiente sem instalação: o `.devcontainer/` do repositório abre um Codespace com o Android Studio (para o `@Preview`) e com o app em janela de celular (Compose Hot Reload). Ver a seção *Como abrir e rodar* do `PASSOS.md`.

---

## Telas do MVP

Partam do backlog da Sprint 0: as histórias P1 dizem quais telas existem. Uma tela bem feita:

- recebe o que mostra e devolve eventos, sem guardar estado próprio (estado elevado);
- usa componentes próprios para o que se repete (um cartão, uma linha, um campo);
- tem `@Preview` com dados fixos, inclusive o caso vazio;
- trata o formulário com validação: botão desabilitado ou mensagem enquanto a entrada for inválida.

Nesta sprint, os dados podem ficar em memória, num estado elevado acima da navegação. Persistência e rede são da Sprint 3; o `ViewModel` chega na Sprint 2.

---

## Navegação

- Cada destino é um tipo `@Serializable`; argumentos são propriedades (`data class Detalhe(val id: Int)`).
- As telas não recebem o `NavController`: recebem lambdas (`onAbrir`, `onVoltar`), e quem navega é o `NavHost`.
- Passe ids nos argumentos, não objetos inteiros.
- Deep link: `navDeepLink<Rota>(basePath = "esquema://host")` no grafo e o `intent-filter` no `AndroidManifest.xml`. Demonstre com `adb shell am start -a android.intent.action.VIEW -d "esquema://host/1"` ou com um link numa página ou notificação.
- Grafos aninhados, se o app tiver um fluxo de várias telas que entra e sai como unidade (cadastro em etapas, por exemplo).

Grupos com alvo iOS: o deep link é demonstrado no simulador, com evidência no vídeo. O esquema é declarado no `Info.plist`, e a URL recebida pelo app é repassada à navegação, como descreve a documentação de deep links do Compose Multiplatform: <https://kotlinlang.org/docs/multiplatform/compose-navigation-deep-links.html>.

---

## Tema, responsividade e adaptatividade

- Material 3 com esquema de cor coerente e os dois modos, claro e escuro. Uma `Surface` (ou `Scaffold`) na raiz garante o fundo e a cor do texto do tema.
- Layout decidido pela **largura da janela**, com `currentWindowAdaptiveInfo().windowSizeClass`, não pela orientação nem por "é tablet".
- Duas larguras no mínimo: compacta (celular em pé) e média ou expandida (celular deitado, tablet, janela desktop). Mostrem as duas no vídeo.
- Sem overflow: textos longos quebram ou encurtam, listas rolam, nada fica cortado na borda. `safeDrawingPadding()` (ou o `Scaffold`) afasta o conteúdo das barras do sistema.

---

## Acessibilidade

- Todo elemento interativo sem texto visível tem `contentDescription` que diz o que ele faz. Imagem decorativa: `contentDescription = null`.
- Linhas que alternam algo são um único elemento (`Modifier.toggleable` com `role`), não uma caixa pequena mais um texto solto.
- Títulos de tela marcados com `semantics { heading() }`.
- Alvos de toque de pelo menos 48 × 48 dp. Os componentes Material 3 já garantem; cuidado com `clickable` em ícones feitos à mão.
- Cores pelos papéis do tema; verificar contraste com o Accessibility Scanner.
- Percorrer as telas com o TalkBack (ou VoiceOver, no iOS) e registrar o que foi corrigido.

---

## Testes de interface

- Ficam em `commonTest` e rodam no alvo desktop: `./gradlew :composeApp:desktopTest`.
- No mínimo 5 testes: um por tela principal, os casos inválido e válido do formulário, e um de navegação (abre, age, volta, confere).
- Encontrem os elementos pelo texto visível ou pela descrição, e ajam como o usuário (`performClick`, `performTextInput`). Assim o teste também verifica a acessibilidade.
- Para testar as duas larguras, separem a decisão de layout do conteúdo (como o `Conteudo(largo)` do exemplo).
- Regras puras (validação, transformação de listas) vão para funções testadas com `kotlin.test`: não contam para os 5 de interface, mas são os testes mais baratos.
- O workflow da Sprint 0 (compilação, `ktlint` e `detekt`) passa a rodar também o `desktopTest`. Os testes de interface não precisam de tela, mas o Skia (que desenha o Compose no desktop) precisa de três bibliotecas do sistema; num runner Linux, instale-as antes dos testes:

  ```yaml
  - run: sudo apt-get update && sudo apt-get install -y libgl1 libegl1 libfontconfig1
  - run: ./gradlew :composeApp:desktopTest
  ```

  Sem elas, os testes falham com `UnsatisfiedLinkError: ... libGL.so.1` (ou `libEGL.so.1`). Verde na branch principal no prazo.

---

## Estrutura do vídeo — 5 minutos

| Tempo | Conteúdo |
|---|---|
| 30 s | O que a sprint entregou, em uma frase por tela |
| 1 min 30 s | Demonstração das telas do MVP e da navegação, incluindo o Voltar |
| 1 min | Deep link funcionando e o layout nas duas larguras |
| 1 min | Acessibilidade: o leitor de tela percorrendo uma tela e o que foi ajustado |
| 1 min | Os testes rodando e o CI verde; o que ficou para a Sprint 2 |

Todos os integrantes devem falar. Link no `README.md`.

---

## Como o grupo é avaliado nesta sprint

- **Entrega técnica (50%)**: a rubrica da Sprint 1, sobre o estado da branch principal no prazo (hash do último commit).
- **Atividade no repositório (30%)**: CI verde, commits distribuídos pelas semanas, ao menos um PR integrado por integrante, PRs revisados por outro integrante e cartões do quadro ligados a PRs. O Fator de Participação individual segue [AVALIACAO.md](AVALIACAO.md#32-fator-de-participação).
- **Comunicação (20%)**: média entre o vídeo e a apresentação da coorte.

O registro de uso de IA continua em `docs/uso-de-ia.md`.
