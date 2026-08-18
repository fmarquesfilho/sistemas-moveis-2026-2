# Guia da Sprint 0 — DIM0524

Prazo em [CRONOGRAMA.md](CRONOGRAMA.md#visão-geral). O que entregar e como é avaliado: [RUBRICAS.md](RUBRICAS.md#sprint-0).

A Sprint 0 é a fase de planejamento: a equipe define o que vai construir, escolhe plataforma-alvo e backend, e deixa o ambiente funcionando.

---

## Visão do produto

Declaração que responde por que o produto existe e qual problema resolve. Use este template:

```
Para [usuários-alvo]
Que [problema ou necessidade]
O [nome do produto] é um [categoria]
Que [benefício principal]
Diferente de [alternativa existente]
Nosso produto [diferencial único]
```

**Exemplo:**

```
Para agentes de campo de secretarias municipais
Que registram vistorias em papel e digitam tudo de novo no escritório
O Vistoria é um aplicativo móvel de registro em campo
Que captura foto, localização e formulário mesmo sem sinal
Diferente de planilhas e formulários de papel
Nosso produto sincroniza sozinho quando a conexão volta
```

Verifique se a visão define o usuário, nomeia um problema específico, explicita o valor único e é realista para um semestre com equipe de até quatro pessoas.

---

## Definição do MVP

O MVP é o escopo mínimo que entrega valor. Declare explicitamente o que fica **fora** — é isso que protege a equipe de estourar o prazo.

**Exemplo, para o Vistoria:**

| No MVP | Fora do MVP |
|---|---|
| Cadastro de vistoria com foto e GPS | Assinatura digital do vistoriado |
| Formulário com validação | Geração de PDF do laudo |
| Funcionamento offline com fila de envio | Painel web de acompanhamento |
| Sincronização ao recuperar conexão | Relatórios e gráficos |
| Lista e busca das vistorias do agente | Múltiplos perfis e permissões |

Enuncie também a hipótese de valor: *acreditamos que [usuários] vão [comportamento] porque [benefício]*.

---

## Backlog inicial

Mínimo de 10 itens no GitHub Projects, ao menos 3 estimados, todos priorizados. Formato de história de usuário: **como [papel], quero [ação] para [benefício]**.

| Prio | História | Critérios de aceitação | Entrega prevista |
|---|---|---|---|
| P1 | Como agente, quero registrar uma vistoria com foto para documentar o local | Formulário com campos obrigatórios validados; foto da câmera anexada; salva localmente | 1 |
| P1 | Como agente, quero ver minhas vistorias para retomar um registro | Lista ordenada por data; indica quais ainda não subiram | 1 |
| P2 | Como agente, quero registrar sem sinal para não depender da rede | Registro salvo em fila; indicador visível de pendência | 3 |
| P2 | Como agente, quero que a localização venha automática para não digitar coordenadas | Permissão solicitada em contexto; funciona com permissão negada | final |

P1 é essencial ao MVP, P2 é importante, P3 é desejável.

---

## Estrutura do vídeo — 5 minutos

| Tempo | Conteúdo |
|---|---|
| 30 s | Equipe: nome, integrantes e o que cada um faz |
| 1 min 30 s | Visão do produto: problema, público e proposta de valor |
| 1 min 30 s | MVP: o que entra, o que fica fora, critérios de sucesso |
| 1 min | Plataforma-alvo e backend escolhidos, **com justificativa** |
| 30 s | Backlog em visão geral e riscos identificados |

Todos os integrantes devem falar.

---

## Estrutura de `docs/proposta.md`

1. Visão do produto, no template
2. Definição do MVP: dentro e fora do escopo
3. Link para backlog inicial, com as histórias priorizadas (pode ser para o próprio repositório caso o backlog esteja registrado nele — o GitHub Projects mora dentro do repositório do GitHub)
4. Plataforma-alvo escolhida — **Android ou iOS** — e justificativa
5. Estratégia de backend escolhida e justificativa — ver [STACK.md](STACK.md#4-escolha-do-backend)
6. Equipe: nome, matrícula e papel de cada integrante
7. Coorte de apresentação e, se houver, integração com outra disciplina

Máximo 3 páginas. A justificativa das escolhas técnicas deve partir das características do produto.

As opções de plataforma-alvo, interface e backend, com seus limites, estão em [STACK.md](STACK.md).

---

## Como começar o projeto

O assistente oficial gera o esqueleto com os alvos já configurados:

```
https://kmp.jetbrains.com
```

Marque os alvos **Android**, **iOS** e **Desktop**, e escolha compartilhar a interface com Compose Multiplatform. O alvo desktop existe para dar o ciclo rápido de edição e visualização com Compose Hot Reload, e funciona em Windows, Linux e macOS.

Rode `kdoctor` antes de abrir uma dúvida sobre ambiente: ele diagnostica a maior parte dos problemas de instalação e diz o que falta.
