import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  idea
  kotlin("jvm") version "1.3.72" // Keep in sync with README
  id("de.fayard.refreshVersions") version "0.8.7"
}

allprojects {
  repositories {
    mavenCentral()
    jcenter()
  }
}

idea.module {
  excludeDirs.add(file("latex"))
  isDownloadJavadoc = true
  isDownloadSources = true
}

subprojects {
  group = "edu.umontreal"
  version = "0.2.5"

  apply(plugin = "org.jetbrains.kotlin.jvm")

  tasks {
    val jvmTarget = JavaVersion.VERSION_1_8.toString()
    compileKotlin {
      kotlinOptions.jvmTarget = jvmTarget
      kotlinOptions.freeCompilerArgs += "-XXLanguage:+NewInference"
    }
    compileTestKotlin {
      kotlinOptions.jvmTarget = jvmTarget
    }
    test {
      minHeapSize = "1024m"
      maxHeapSize = "4096m"
      useJUnitPlatform()
      testLogging {
        events = setOf(FAILED, PASSED, SKIPPED, STANDARD_OUT)
        exceptionFormat = FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
      }
    }
  }
}
