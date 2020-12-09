// additional properties
val dokkaVersion = "1.4.10.2"
val kotlinVersion = "1.4.21"
val spekVersion = "2.0.12"

plugins {
    `java-library`

    id("org.jetbrains.kotlin.jvm") version "1.4.20"
    id("org.jetbrains.dokka") version "1.4.10.2"
}

group = "mip"
version = "0.1.0"

repositories {
    maven("https://dl.bintray.com/kyonifer/maven")
    maven("https://dl.bintray.com/spekframework/spek-dev")

    mavenCentral()
    jcenter()
}

configurations {
    implementation {
        resolutionStrategy.failOnVersionConflict()
    }
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.github.jnr:jnr-ffi:2.1.15")
    // implementation("com.github.jnr:jnr-posix:3.0.54")
    // implementation("com.github.jnr:jnr-constants:0.9.15")

    // implementation("org.bytedeco:javacpp:1.5.3")

    // implementation("com.kyonifer:koma-core-ejml:0.12")
    // implementation("com.kyonifer:koma-plotting:0.12")

    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:$dokkaVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
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

    // dokka {
    //     outputFormat = "javadoc"
    //     outputDirectory = "$buildDir/javadoc"
    //
    //     configuration {
    //         includes = listOf("README.md")
    //     }
    // }

    test {
        useJUnitPlatform {
            includeEngines("spek2")
        }
        // exclude("**/*")
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
