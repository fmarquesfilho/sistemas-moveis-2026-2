// Projeto KMP (Kotlin Multiplatform) com Compose Multiplatform.
// Alvos: Android (roda no emulador/aparelho) e Desktop (janela JVM, Hot Reload).
// A UI fica em composeApp/src/commonMain e é a MESMA nos dois alvos.
//
// Os plugins são declarados aqui e APLICADOS no módulo :composeApp.
plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinSerialization) apply false
}
