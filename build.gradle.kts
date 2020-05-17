plugins {
    kotlin("jvm") version "1.4-M1"
}

group = "br.ufop.jmip"
version = "0.1"

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.github.jnr:jnr-ffi:2.1.12")
    implementation("com.github.jnr:jnr-posix:3.0.54")
    implementation("com.github.jnr:jnr-constants:0.9.15")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}