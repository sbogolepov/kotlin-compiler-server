import org.jetbrains.kotlin.gradle.utils.NativeCompilerDownloader

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
    // For Analysis API components
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-ide-plugin-dependencies")
    mavenLocal()
}

val kotlinVersion = rootProject.properties["systemProp.kotlinVersion"]

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-compiler:$kotlinVersion")
    // For K/N Distribution class
    implementation("org.jetbrains.kotlin:kotlin-native-utils:$kotlinVersion")

    // Analysis API components which are required for the Swift export
    implementation("org.jetbrains.kotlin:analysis-api-standalone-for-ide:$kotlinVersion") { isTransitive = false }
    implementation("org.jetbrains.kotlin:high-level-api-for-ide:$kotlinVersion") { isTransitive = false }
    implementation("org.jetbrains.kotlin:high-level-api-fir-for-ide:$kotlinVersion") { isTransitive = false }
    implementation("org.jetbrains.kotlin:high-level-api-impl-base-for-ide:$kotlinVersion") { isTransitive = false }
    implementation("org.jetbrains.kotlin:low-level-api-fir-for-ide:$kotlinVersion") { isTransitive = false }
    implementation("org.jetbrains.kotlin:symbol-light-classes-for-ide:$kotlinVersion") { isTransitive = false }

    // Swift export not-yet-published dependencies.
    implementation("org.jetbrains.kotlin:sir:2.0.255-SNAPSHOT") { isTransitive = false }
    implementation("org.jetbrains.kotlin:sir-passes:2.0.255-SNAPSHOT") { isTransitive = false }
    implementation("org.jetbrains.kotlin:sir-compiler-bridge:2.0.255-SNAPSHOT") { isTransitive = false }
    implementation("org.jetbrains.kotlin:sir-printer:2.0.255-SNAPSHOT") { isTransitive = false }
    implementation("org.jetbrains.kotlin:swift-export-standalone:2.0.255-SNAPSHOT") { isTransitive = false }

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
}

tasks.create("downloadKotlinNativeDistribution") {
    doLast {
        val kotlinNativeHome = NativeCompilerDownloader(this.project).also { it.downloadIfNeeded() }.compilerDirectory
        project.ext.set("kotlinNativeHome", kotlinNativeHome)
    }
}

tasks.test {
    dependsOn("downloadKotlinNativeDistribution")
    doFirst {
        systemProperty("kotlin.native.home", project.ext.get("kotlinNativeHome")!!)
    }
}