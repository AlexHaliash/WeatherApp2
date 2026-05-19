import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

val openWeatherApiKeyProvider =
    providers.gradleProperty("OPENWEATHER_API_KEY").orElse("87d4e4891f75644673e9e144d9b0f46c")

val generateWeatherApiKey = tasks.register("generateWeatherApiKey") {
    val outputDir = layout.buildDirectory.dir("generated/weather")
    outputs.dir(outputDir)
    doLast {
        val apiKey = openWeatherApiKeyProvider.get()
        val dir = outputDir.get().asFile
        dir.mkdirs()
        dir.resolve("GeneratedApiKey.kt").writeText(
            """
            package com.example.weatherapp.config

            internal object GeneratedApiKey {
                const val OPENWEATHER_API_KEY: String = "$apiKey"
            }
            """.trimIndent() + "\n",
        )
    }
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    jvm()

    js {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    androidLibrary {
        namespace = "com.example.weatherapp.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        androidResources {
            enable = true
        }
        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.compose.uiToolingPreview)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.cio)
        }
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
            implementation(libs.wrappers.browser)
        }
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
        commonMain {
            kotlin.srcDir(layout.buildDirectory.dir("generated/weather"))
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.multiplatform.settings)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.multiplatform.settings)
            implementation("com.russhwolf:multiplatform-settings-test:${libs.versions.multiplatform.settings.get()}")
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.mock)
            implementation(libs.kotlinx.serialization.json)
        }
        jvmTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.multiplatform.settings)
        }
    }
}

kotlin.sourceSets.named("androidHostTest") {
    dependencies {
        implementation(libs.kotlin.test)
        implementation(libs.junit)
        implementation(libs.compose.ui.test)
        implementation(libs.androidx.lifecycle.viewmodelCompose)
        implementation("com.russhwolf:multiplatform-settings-test:${libs.versions.multiplatform.settings.get()}")
        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.mock)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.client.serialization)
        implementation(libs.kotlinx.serialization.json)
    }
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
    dependsOn(generateWeatherApiKey)
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}
