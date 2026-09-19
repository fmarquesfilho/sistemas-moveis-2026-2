import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization) // rotas tipadas da navegação
}

kotlin {
    // Alvo Android — o que roda no emulador.
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }
            }
        }
    }

    // Alvo Desktop (JVM) — janela, com o ciclo rápido de Hot Reload.
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        // A UI compartilhada. É AQUI que ficam App.kt e os componentes.
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation("org.jetbrains.compose.ui:ui-tooling-preview:${libs.versions.compose.get()}") // @Preview em commonMain
            implementation(libs.navigation.compose) // Navigation Compose (21/09)
            implementation(libs.material3.adaptive) // classes de tamanho de janela (21/09)
            implementation(libs.kotlinx.serialization.json)
        }
        // Testes compartilhados: kotlin.test e testes de interface do Compose (21/09).
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.compose.ui.test)
        }
        androidMain.dependencies {
            implementation("org.jetbrains.compose.ui:ui-tooling:${libs.versions.compose.get()}") // render do @Preview no Android Studio
            implementation(libs.androidx.activity.compose) // setContent { App() }
        }
        // Os testes de interface de commonTest rodam no alvo desktop: ./gradlew :composeApp:desktopTest
        val desktopTest by getting {
            dependencies { implementation(compose.desktop.currentOs) }
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation("org.jetbrains.compose.hot-reload:hot-reload-runtime-api:1.2.0") // @DevelopmentEntryPoint
        }
    }
}

android {
    namespace = "br.ufrn.exemplo.tarefas"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "br.ufrn.exemplo.tarefas"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    // Chave de debug versionada: o APK de debug sai com a mesma assinatura em qualquer
    // máquina (Mac, laboratório, Codespace) e atualiza por cima sem desinstalar.
    // Serve só para debug; nunca para publicar.
    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("keystore/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Empacotamento do alvo Desktop (task `:composeApp:run`).
compose.desktop {
    application {
        mainClass = "br.ufrn.exemplo.tarefas.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "br.ufrn.exemplo.tarefas"
            packageVersion = "1.0.0"
        }
    }
}
