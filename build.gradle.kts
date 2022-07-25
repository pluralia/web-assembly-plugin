import org.jetbrains.grammarkit.tasks.GenerateLexer
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.intellij") version "1.5.2"
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("org.jetbrains.grammarkit") version "2021.1.3"
}

group = "org.jetbrains.webstorm"
version = "1.4.222"

repositories {
    mavenCentral()
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


tasks {
    patchPluginXml {
        sinceBuild.set("222.0")
        untilBuild.set("222.*")
    }

    withType<KotlinCompile> {
        dependsOn(
            generateWebAssemblyLexer
        )
    }
}
