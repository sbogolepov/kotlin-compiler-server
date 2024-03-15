import org.jetbrains.kotlin.konan.target.Distribution
import org.jetbrains.kotlin.swiftexport.standalone.SwiftExportConfig
import org.jetbrains.kotlin.swiftexport.standalone.SwiftExportInput
import org.jetbrains.kotlin.swiftexport.standalone.SwiftExportOutput
import org.jetbrains.kotlin.swiftexport.standalone.runSwiftExport
import java.io.File
import java.nio.file.Path

fun swiftExportServerExecutor(
    konanHome: String,
    sourceFile: Path,
    swiftApiOutput: Path,
    kotlinBridges: Path,
    cBridges: Path,
) {
    val input = SwiftExportInput(sourceFile)
    val output = SwiftExportOutput(swiftApi = swiftApiOutput, kotlinBridges = kotlinBridges, cHeaderBridges = cBridges)
    val config = SwiftExportConfig(
        distribution = Distribution(
            konanHome = konanHome
        ),
    )
    runSwiftExport(input, config, output)
}