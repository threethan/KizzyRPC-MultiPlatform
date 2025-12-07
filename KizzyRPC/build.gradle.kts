import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.kotlinx.serialization)

}

group = "io.github.kotlin"
version = "1.0.0"

kotlin {
    jvm()
    @Suppress("UnstableApiUsage")
    androidLibrary {
        namespace = "org.jetbrains.kotlinx.multiplatform.library.template"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava() // enable java compilation support
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()

    sourceSets {
        commonMain.dependencies {
            implementation (libs.kotlinx.coroutine)
            implementation (libs.bundles.network.ktor)
            implementation (libs.ktor.websockets)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "library", version.toString())

    pom {
        name = "KizzyRPC-MultiPlatform"
        description = "An unofficial port of the KizzyRPC library for Discord rich presence to Kotlin MultiPlatform."
        inceptionYear = "2025"
        url = "https://github.com/threethan/kizzyrpc-multiplatform/"
        licenses {
            license {
                name = "Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "dead8309"
                name = "Vaibhav Raj"
                url = "https://github.com/dead8309"
            }
            developer {
                id = "threethan"
                name = "Ethan Medeiros"
                url = "https://github.com/threethan"
            }
        }
        scm {
            url = "https://github.com/threethan/kizzyrpc-multiplatform/"
            connection = "scm:git:git@github.com:threethan/kizzyrpc-multiplatform.git"
            developerConnection = "scm:git:git@github.com:threethan/kizzyrpc-multiplatform.git"
        }
    }
}
