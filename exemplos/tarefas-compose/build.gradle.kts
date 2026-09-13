// Exemplo de UI em Compose — o alvo DESKTOP da mesma UI que roda no Android.
// Em aula, o projeto vem do assistente KMP (Android Studio) com os alvos Android e
// Desktop; a UI (este App.kt) fica em commonMain e é IDÊNTICA. Aqui isolamos o
// desktop para dar o ciclo rápido de edição/visualização (Hot Reload e @Preview).
// Versões acompanham o MUSI (Kotlin 2.4.10, Compose Multiplatform 1.12.0).

plugins {
    kotlin("jvm") version "2.4.10"
    id("org.jetbrains.compose") version "1.12.0"
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.10"
}

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
}

kotlin { jvmToolchain(25) }

compose.desktop {
    application { mainClass = "br.ufrn.exemplo.tarefas.MainKt" }
}
