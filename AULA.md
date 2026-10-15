# Branch `aula` — o exemplo de Tarefas, passo a passo

Este branch guarda o exemplo de Tarefas (Compose Multiplatform) em cada etapa da construção.
O branch começa no **Passo 10**, onde a Sprint 1 terminou, e cada passo da Sprint 2 está
numa tag (`passo-11` a `passo-16`). Os passos da Sprint 1 continuam nas tags `passo-03` a
`passo-10`.

## Como usar

Crie o Codespace a partir deste branch (**Code → Codespaces → New with options → Branch:
`aula`**) ou, no seu computador:

```bash
git fetch --tags origin
git switch aula
```

Escreva o código de cada passo a partir do anterior. Para conferir ou alcançar a turma:

```bash
./passo.sh        # lista os passos e mostra em qual o código está
./passo.sh 6      # leva o código para o estado final do Passo 6
```

O `./passo.sh` guarda o que você tinha digitado (`git stash list`) antes de trocar.

## Comandos

Todos em `exemplos/tarefas-compose`. No Codespace, a janela aparece na área de trabalho do
navegador (aba **Portas**, porta 6080, senha `vscode`), e por isso os comandos de janela
levam `DISPLAY=:1` na frente.

| Para | No Codespace | No seu computador |
|---|---|---|
| Abrir o app com Hot Reload | `DISPLAY=:1 ./gradlew :composeApp:hotRunDesktop --auto` | `./gradlew :composeApp:hotRunDesktop --auto` |
| Rodar os testes (Passo 9 em diante) | `./gradlew :composeApp:desktopTest` | o mesmo |
| Abrir o Android Studio (`@Preview`) | `bash ../../.devcontainer/android-studio.sh` | abra a pasta no Android Studio |

Do Passo 10 em diante, os mesmos comandos têm nome: `mise run app`, `mise run test`,
`mise run studio`. Do Passo 14 em diante, o app busca as tarefas na API de Web II; no
Codespace, sem a API, ele roda com o repositório em memória (`TAREFAS_API=memoria`).

## O que muda em cada passo

| Passo | Assunto | Diferença em relação ao passo anterior |
|---|---|---|
| 4 | Formulário com validação | [passo-03...passo-04](../../compare/passo-03...passo-04) |
| 5 | Material 3: tema claro e escuro | [passo-04...passo-05](../../compare/passo-04...passo-05) |
| 6 | Acessibilidade: a linha inteira é a caixa de seleção | [passo-05...passo-06](../../compare/passo-05...passo-06) |
| 7 | Navegação com rotas tipadas e deep link | [passo-06...passo-07](../../compare/passo-06...passo-07) |
| 8 | Janela compacta × larga | [passo-07...passo-08](../../compare/passo-07...passo-08) |
| 9 | Testes: regras e interface | [passo-08...passo-09](../../compare/passo-08...passo-09) |
| 10 | O ambiente em tasks do mise | [passo-09...passo-10](../../compare/passo-09...passo-10) |
| 11 | ViewModel com `StateFlow` | [passo-10...passo-11](../../compare/passo-10...passo-11) |
| 12 | Corrotinas e estado selado | [passo-11...passo-12](../../compare/passo-11...passo-12) |
| 13 | Camadas e Koin | [passo-12...passo-13](../../compare/passo-12...passo-13) |
| 14 | Ktor Client contra a API de Web II | [passo-13...passo-14](../../compare/passo-13...passo-14) |
| 15 | Cache local com DataStore | [passo-14...passo-15](../../compare/passo-14...passo-15) |
| 16 | Testes de ViewModel com Turbine | [passo-15...passo-16](../../compare/passo-15...passo-16) |

A explicação de cada passo está em `exemplos/tarefas-compose/PASSOS.md`.
