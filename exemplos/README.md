# Exemplos da aula — Compose do zero

Uma tela de **tarefas** construída incrementalmente em Compose Multiplatform. Serve à
aula de **14/09** (conclui os fundamentos da Sprint 0 e abre a Sprint 1: listas,
formulários e Material 3). É menor que o `app/` do MUSI de propósito: cabe numa aula.

| Pasta | O que é | Rodar |
|-------|---------|-------|
| [`tarefas-compose/`](tarefas-compose/PASSOS.md) | O alvo **Desktop** da mesma UI que roda no Android | `./gradlew run` |

## Em aula: Android Studio, não Codespaces

O laboratório tem **Android Studio** instalado. Usamos ele porque **Hot Reload** e
**`@Preview`** funcionam bem localmente — o que não acontece no Codespaces.

Fluxo da aula:

1. **New Project → Kotlin Multiplatform** (alvos Android + Desktop, *Share UI*)
2. A UI compartilhada fica em `composeApp/src/commonMain/kotlin`
3. Rodar no **androidApp** (emulador) ou no **desktopApp [hot] 🔥**
4. Construir os 5 passos do [PASSOS.md](tarefas-compose/PASSOS.md)

> Esta pasta isola o alvo Desktop só para servir de **referência que compila**. O
> `App.kt` daqui é idêntico ao que vai em `commonMain` no projeto do Android Studio.

Versões acompanham o MUSI (Kotlin 2.4.10, Compose Multiplatform 1.12.0).
