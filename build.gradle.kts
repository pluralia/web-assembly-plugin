import org.jetbrains.grammarkit.tasks.GenerateLexer
import org.jetbrains.grammarkit.tasks.GenerateParser
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.intellij.tasks.RunIdeTask

plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    java
    kotlin("jvm") version "1.3.72"
    id("org.jetbrains.grammarkit") version "2020.2.1"
}

group = "com.jetbrains.webstorm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit", "junit", "4.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "IU-2020.1.2"
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
      Add change notes here.<br>
      <em>most HTML tags may be used</em>""")
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

val generateWebAssemblyParser = task<GenerateParser>("generateWebAssemblyParser") {
    source = "src/main/grammars/WebAssemblyParser.bnf"
    targetRoot = "src/main/gen"
    pathToParser = "/org/jetbrains/webstorm/lang/parser/WebAssemblyParser.java"
    pathToPsiRoot = "/org/jetbrains/webstorm/lang/psi"
    purgeOldFiles = true
}

tasks.withType<KotlinCompile> {
    dependsOn(
            generateWebAssemblyLexer,
            generateWebAssemblyParser
    )
}
