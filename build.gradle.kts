import org.jetbrains.grammarkit.tasks.GenerateLexer
import org.jetbrains.grammarkit.tasks.GenerateParser
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.intellij") version "1.3.0"
    java
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.grammarkit") version "2021.1.3"
}


group = "org.jetbrains.webstorm"
version = "1.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit", "junit", "4.12")
//    implementation("dk.brics.automaton", "automaton", "1.11-8")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("IU-LATEST-EAP-SNAPSHOT")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    test {
        systemProperty("idea.home.path", "\$HOME/IdeaProjects")
    }
}

sourceSets {
    main {
        java.srcDirs("src/main/gen")
    }
}

val generateWebAssemblyLexer = task<GenerateLexer>("generateWebAssemblyLexer") {
    source = "src/main/grammars/WebAssemblyLexer.flex"
    targetDir = "src/main/gen/org/jetbrains/webstorm/lang/lexer"
    targetClass = "_WebAssemblyLexer"
    purgeOldFiles = true
}

tasks.withType<KotlinCompile> {
    dependsOn(
            generateWebAssemblyLexer,
    )
}
