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

## Roteiro da quarta, 21/10: passos 11 e 12

Na raiz do repositório (`/workspaces/sistemas-moveis-2026-2`), com a área de trabalho aberta (porta 6080). Os comandos `mise run` funcionam de qualquer pasta do repositório.

```bash
./passo.sh 11
mise run app       # o app, agora com o estado num ViewModel
mise run test      # 11 testes, entre eles os do ViewModel, sem tela
./passo.sh 12
mise run app       # carregando por 800 ms, depois a lista
```

No Passo 12, em `RepositorioEmMemoria`, troque `falhar` para `true` (tela de erro e **Tentar de novo**) e depois a lista inicial por uma vazia ("Nenhuma tarefa ainda").

## Roteiro da segunda, 26/10: passos 13 a 16

Também na raiz do repositório.

| Passo | Comandos |
|---|---|
| 13 | `./passo.sh 13` · `mise run app` · `mise run test` (inclui o `ModulosTest`) |
| 14 | `./passo.sh 14` · com a API de Web II no ar, `mise run app`: as tarefas vêm do banco. No Codespace, sem a API, o app usa o repositório em memória; para ver a tela de erro, `TAREFAS_API=http://localhost:9999 mise run app` |
| 15 | `./passo.sh 15` · abra o app com a API no ar, feche, pare a API e abra de novo: "mostrando a última lista salva" |
| 16 | `./passo.sh 16` · `mise run test` (25 testes) |

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
