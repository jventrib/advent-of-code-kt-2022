plugins {
    kotlin("multiplatform") version "1.7.20"
    kotlin("plugin.serialization") version "1.9.10"
}

repositories {
    mavenCentral()
}

kotlin {
    macosX64()
    mingwX64()
    linuxX64()
    mingwX64()
    jvm {
        withJava()
        jvm {
            testRuns["test"].executionTask.configure {
                useJUnitPlatform()
            }
        }
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
            }
        }
        val desktopMain by creating {
            dependsOn(commonMain)
        }
        val linuxX64Main by getting {
            dependsOn(desktopMain)
        }
        val mingwX64Main by getting {
            dependsOn(desktopMain)
        }
        val macosX64Main by getting {
            dependsOn(desktopMain)
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}


//tasks {
//    test {
//        useJUnitPlatform()
////        setExcludes(listOf("**"))
//    }
//
//    wrapper {
//        gradleVersion = "7.3"
//    }
//
//    dependencies {
//        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
//        implementation("com.github.ajalt.mordant:mordant:2.0.0-beta4")
//        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
//        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
//    }
//}

//val compileKotlin: KotlinCompile by tasks
//compileKotlin.kotlinOptions {
//    freeCompilerArgs = listOf(
//        "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
//        "-Xuse-experimental=kotlinx.coroutines.FlowPreview"
//    )
//
//}
