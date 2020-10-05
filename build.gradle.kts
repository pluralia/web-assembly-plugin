import org.jetbrains.grammarkit.tasks.GenerateLexer
import org.jetbrains.grammarkit.tasks.GenerateParser
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    java
    kotlin("jvm") version "1.3.72"
    id("org.jetbrains.grammarkit") version "2020.2.1"
}

group = "org.jetbrains.webstorm"
version = "1.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit", "junit", "4.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "IU-2020.2"
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

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
      <p>Fixed parsing issue for the memory instruction</p>
      <p>Updated icon for the file type</p>
      """)
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
