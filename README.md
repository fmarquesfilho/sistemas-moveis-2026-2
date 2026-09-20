# DIM0524 — Desenvolvimento de Sistemas para Dispositivos Móveis

**Bacharelado em Engenharia de Software — UFRN/DIMAp**
**Período letivo 2026.2 — 17/08/2026 a 19/12/2026**
**Turma 01 · Segundas e quartas, 14:50 às 16:30 (24T34)**
**Docente**: Fernando Figueira Filho — fernando.figueira@ufrn.br

As aulas de 10/08, 12/08 e 17/08 não foram realizadas. O curso inicia em 19/08.

---

## Documentos da disciplina

Cada informação vive em um único documento. Em caso de divergência, vale o documento indicado abaixo.

| Documento | Conteúdo |
|-----------|----------|
| [Plano de Curso](docs/PLANO_DE_CURSO.md) | Ementa, objetivos, conteúdo programático e bibliografia |
| [Cronograma](docs/CRONOGRAMA.md) | **Todas as datas** e o conteúdo de cada aula |
| [Sistemática de Avaliação](docs/AVALIACAO.md) | **Pesos e regras de nota**, bônus, grupos, provas e integridade acadêmica |
| [Rúbricas](docs/RUBRICAS.md) | **O que entregar** em cada sprint e como é avaliado |
| [Guia da Sprint 0](docs/SPRINT-0.md) | Templates de visão do produto, MVP e backlog |
| [Guia da Sprint 1](docs/SPRINT-1.md) · [tarefas](docs/SPRINT-1-TAREFAS.md) | Telas, navegação, responsividade, acessibilidade e testes de interface |
| [Leituras](leituras/) | Guias de leitura de cada aula (`moveis-s1-pte1.md`, `moveis-s1-pte2.md`, ...) |
| [Stack Tecnológica](docs/STACK.md) | **Kotlin Multiplatform e Compose Multiplatform**, bibliotecas, backend, plataforma-alvo e ambiente |

---

## Projeto

Equipes de 1 a 4 estudantes desenvolvem um aplicativo multiplataforma em Kotlin ao longo do semestre, em repositório **público** no GitHub. Publicar na App Store ou na Play Store não é exigido em nenhum momento.

O semestre é organizado em uma Sprint 0, três sprints de projeto e um bloco final, com apresentação de todos os grupos ao fim de cada sprint. Datas em [docs/CRONOGRAMA.md](docs/CRONOGRAMA.md); composição das notas em [docs/AVALIACAO.md](docs/AVALIACAO.md).

---

## Exemplo das aulas

[`exemplos/tarefas-compose`](exemplos/tarefas-compose) é uma tela construída do zero,
passo a passo, em Compose Multiplatform, com os alvos **Android** e **Desktop**. O
[`PASSOS.md`](exemplos/tarefas-compose/PASSOS.md) reconstrói o exemplo do primeiro
`@Composable` aos testes de interface.

### Como rodar

O [`mise.toml`](mise.toml) da raiz fixa a versão do JDK e dá um nome curto a cada comando:

```bash
mise install          # uma vez: baixa o JDK
mise tasks            # a lista completa

mise run app          # a tela no desktop, com Hot Reload
mise run app:celular  # a mesma tela numa janela de celular
mise run test         # regras e interface, no alvo desktop (sem emulador)
mise run apk          # APK de debug (precisa do SDK do Android)
```

O `mise` é conveniência, não requisito: cada task mostra, no `mise.toml`, o comando
completo (`./gradlew :composeApp:hotRunDesktop --auto`) que continua valendo. Instalação:
[mise.jdx.dev](https://mise.jdx.dev/getting-started.html).

**Sem instalar nada:** crie um Codespace (Code → Codespaces). O
[`.devcontainer/`](.devcontainer) traz o JDK, o SDK do Android, o `mise` e uma área de
trabalho no navegador (aba **Portas** → **6080**), onde a janela do app e o Android Studio
aparecem. Lá, `mise run studio` abre o Android Studio para ver o `@Preview`.

---

## Licença

Material licenciado sob [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/).
