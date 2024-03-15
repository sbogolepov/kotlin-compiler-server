package com.compiler.server.compiler.components

import com.compiler.server.model.CompilerDiagnostics
import com.compiler.server.model.SwiftExportResult
import component.KotlinEnvironment
import org.jetbrains.kotlin.psi.KtFile
import org.springframework.stereotype.Component
import swiftExportServerExecutor
import kotlin.io.path.absolutePathString
import kotlin.io.path.div
import kotlin.io.path.readText

@Component
class KotlinToSwiftTranslator(
    private val kotlinNativeHomeProvider: KotlinNativeHomeProvider
) {
    fun translate(files: List<KtFile>, arguments: List<String>): SwiftExportResult {
        return usingTempDirectory { tempDirectory ->
            val ioFiles = files.writeToIoFiles(tempDirectory)
            val cBridges = tempDirectory / "cBridges.h"
            val kotlinBridges = tempDirectory / "kotlinBridges.kt"
            val swiftApi = tempDirectory / "swiftApi.swift"
            val konanHome = kotlinNativeHomeProvider.kotlinNativeHome
            swiftExportServerExecutor(
                swiftApiOutput = swiftApi,
                sourceFile = ioFiles.first(),
                kotlinBridges = kotlinBridges,
                cBridges = cBridges,
                konanHome = konanHome
            )
            SwiftExportResult(
                compilerDiagnostics = CompilerDiagnostics(emptyMap()),
                swiftCode = swiftApi.readText()
            )
        }
    }
}