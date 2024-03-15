
import java.nio.file.Path
import kotlin.io.path.*
import kotlin.test.Test
import kotlin.test.assertEquals

class SwiftExportTests {
    @Test
    fun smoke() {
        val tempDir = createTempDirectory()

        val input = """
            fun foo(): UInt = 5
        """.trimIndent()

        val inputFile = (tempDir / "input.kt").also { it.writeText(input) }
        val konanHome = System.getProperty("kotlin.native.home")

        val swiftApiOutput = Path.of(tempDir.pathString, "swiftApiOutput")
        swiftExportServerExecutor(
            konanHome = konanHome,
            sourceFile = inputFile,
            swiftApiOutput = swiftApiOutput,
            kotlinBridges = Path.of(tempDir.pathString,"kotlinBridges"),
            cBridges = Path.of(tempDir.pathString,"cBridges")
        )

        val expected = """
            import KotlinBridges
            import KotlinRuntime

            public func foo() -> Swift.UInt32 {
                return __root___foo()
            }
        """.trimIndent().trimEnd()

        assertEquals(expected, swiftApiOutput.readText().trimEnd())

    }
}