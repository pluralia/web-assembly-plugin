plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    java
    kotlin("jvm") version "1.3.72"
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
//
//sourceSets {
//    main {
//        java.srcDirs("src/gen")
//        kotlin.srcDirs("src/$platformVersion/main/kotlin")
//        resources.srcDirs("src/$platformVersion/main/resources")
//    }
//    test {
//        kotlin.srcDirs("src/$platformVersion/test/kotlin")
//        resources.srcDirs("src/$platformVersion/test/resources")
//    }
//}

sourceSets {
    main {
        java.srcDirs("src/main/gen")
    }
}