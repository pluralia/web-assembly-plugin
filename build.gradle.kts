plugins {
    id("org.jetbrains.intellij") version "1.10.0"
    id("org.jetbrains.kotlin.jvm") version "1.7.21"
    id("org.jetbrains.grammarkit") version "2022.3"
}

group = "org.jetbrains.webstorm"
version = "1.4.223"

repositories {
    mavenCentral()
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("IU-LATEST-EAP-SNAPSHOT")
}

sourceSets {
    main {
        java.srcDirs("src/main/gen")
    }
}

tasks {
    compileKotlin {
    }

    compileTestKotlin {
    }

    test {
        systemProperty("idea.home.path", "\$HOME/IdeaProjects")
    }
}

tasks {
    patchPluginXml {
        sinceBuild.set("223.0")
        untilBuild.set("223.*")
    }
}
