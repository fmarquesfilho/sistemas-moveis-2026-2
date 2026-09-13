# Exemplos da aula — Compose do zero

Uma tela de **tarefas** construída incrementalmente em Compose Multiplatform. Serve à
aula de **14/09** (conclui os fundamentos da Sprint 0 e abre a Sprint 1: listas,
formulários e Material 3). É menor que o `app/` do MUSI de propósito: cabe numa aula.

| Pasta | O que é | Rodar |
|-------|---------|-------|
| [`tarefas-compose/`](tarefas-compose/PASSOS.md) | Projeto **KMP** completo (Android + Desktop), UI compartilhada em `commonMain` | Android Studio (emulador) ou `./gradlew :composeApp:run` (desktop) |

## Em aula: Android Studio, não Codespaces

O laboratório tem **Android Studio** instalado. Usamos ele porque **Hot Reload** e
**`@Preview`** funcionam bem localmente — o que não acontece no Codespaces.

1. Abra a pasta `tarefas-compose/` no Android Studio (deixe o Gradle sincronizar).
2. **Emulador:** run configuration **composeApp** → escolha o emulador → **Run ▶**.
3. **Desktop:** `./gradlew :composeApp:run` (ou a config de desktop no IDE).
4. A UI fica em `composeApp/src/commonMain/kotlin`; construa os 5 passos do [PASSOS.md](tarefas-compose/PASSOS.md).

## Versões (validadas neste projeto)

Kotlin 2.4.10 · Compose Multiplatform 1.12.0 · **AGP 9.1.0** · **compileSdk 37** · minSdk 24.
Compila para **desktop** e **Android** (APK `debug` gerado com sucesso).

> O `local.properties` (caminho do Android SDK) é gerado pelo Android Studio e fica fora
> do git. Com AGP 9, o módulo de aplicação KMP usa `android.builtInKotlin=false` e
> `android.newDsl=false` (já no `gradle.properties`).
