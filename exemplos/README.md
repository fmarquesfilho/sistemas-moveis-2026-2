# Exemplos da aula — Compose do zero

Uma tela de **tarefas** construída incrementalmente em Compose Multiplatform, em 10 passos:
listas, formulários e Material 3 (aula de 14/09); acessibilidade, navegação com deep link,
layout adaptativo, testes de interface e tasks do `mise` (segunda parte da Sprint 1, em
vídeo). É menor que o `app/` do MUSI de propósito: cabe numa aula.

| Pasta | O que é | Rodar |
|-------|---------|-------|
| [`tarefas-compose/`](tarefas-compose/PASSOS.md) | Projeto **KMP** completo (Android + Desktop), UI compartilhada em `commonMain` | Android Studio (emulador) ou `./gradlew :composeApp:run` (desktop) |

## Onde rodar

O exemplo usa AGP 9.1, que exige o **Android Studio Panda 2 (2025.3.2) ou mais novo**. Enquanto
o Android Studio do laboratório não for atualizado, use o **Codespaces** deste repositório:
o Android Studio (com `@Preview`) e o app com Hot Reload abrem numa área de trabalho no
navegador. O passo a passo está na seção *Como abrir e rodar* do
[PASSOS.md](tarefas-compose/PASSOS.md).

Na primeira vez, o Android Studio do Codespace pergunta sobre o envio de estatísticas e se
você confia no projeto (**Trust Project**); depois sincroniza o Gradle sozinho. O primeiro
`@Preview` pede um build (**Build & Refresh**), que leva alguns minutos.

## Versões (validadas neste projeto)

Kotlin 2.4.10 · Compose Multiplatform 1.12.0 · **AGP 9.1.0** · **compileSdk 37** · minSdk 24.
Compila para **desktop** e **Android** (APK `debug` gerado com sucesso).

> O `local.properties` (caminho do Android SDK) é gerado pelo Android Studio e fica fora
> do git. Com AGP 9, o módulo de aplicação KMP usa `android.builtInKotlin=false` e
> `android.newDsl=false` (já no `gradle.properties`).
