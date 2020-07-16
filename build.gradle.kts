plugins {
    `java-library`
    kotlin("jvm") version "1.4-M1"
    id("org.jetbrains.dokka") version "0.10.0"
}

group = "mip"
version = "0.1.0"

repositories {
    maven("https://dl.bintray.com/kyonifer/maven")
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    jcenter()
    mavenCentral()
}

configurations {
    implementation {
        resolutionStrategy.failOnVersionConflict()
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.jnr:jnr-ffi:2.1.15")

    // implementation("org.bytedeco:javacpp:1.5.3")
    // implementation("com.github.jnr:jnr-posix:3.0.54")
    // implementation("com.github.jnr:jnr-constants:0.9.15")

    // implementation("com.kyonifer:koma-core-ejml:0.12")
    // implementation("com.kyonifer:koma-plotting:0.12")

    // implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        kotlinOptions {
            includeRuntime = true
            jvmTarget = "1.8"
            suppressWarnings = false
        }
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    jar {
        manifest {
            attributes(
                "Implementation-Title" to "Kotlin-MIP",
                "Implementation-Version" to project.version
            )
        }

        archiveClassifier.set("uber")
        archiveFileName.set("${rootProject.name}-${project.version}.jar")
        from(sourceSets.main.get().output)
        dependsOn(configurations.runtimeClasspath)
        destinationDirectory.set(rootDir)
        from(configurations.runtimeClasspath.get()
            .filter {
                it.name.endsWith("jar") && !it.name.startsWith("kotlin")
                    && !it.name.startsWith("annotations")
            }.map { zipTree(it) }
        )
    }

    dokka {
        outputFormat = "javadoc"
        outputDirectory = "$buildDir/javadoc"

        configuration {
            includes = listOf("README.md")
        }
    }

    test {
        // exclude("**/*")
        ignoreFailures = true
        jvmArgs = listOf("-ea", "-Xss8m")
    }
}

tasks.register<Jar>("queens") {
    archiveClassifier.set("uber")
    archiveFileName.set("queens.jar")
    dependsOn(configurations.runtimeClasspath)
    destinationDirectory.set(rootDir)
    from(configurations.runtimeClasspath.get()
        .map { if (it.isDirectory) it else zipTree(it) }
    )
    from(sourceSets.main.get().output)
    manifest {
        attributes["Main-Class"] = "mip.examples.QueensKt"
    }
}
