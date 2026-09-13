import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
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
        }
        androidMain.dependencies {
            implementation("org.jetbrains.compose.ui:ui-tooling:${libs.versions.compose.get()}") // render do @Preview no Android Studio
            implementation(libs.androidx.activity.compose) // setContent { App() }
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
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
