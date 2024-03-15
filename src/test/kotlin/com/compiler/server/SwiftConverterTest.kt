package com.compiler.server

import com.compiler.server.base.BaseExecutorTest
import org.junit.jupiter.api.Test
import kotlin.test.assertContains

class SwiftConverterTest : BaseExecutorTest() {

    @Test
    fun basicSwiftExportTest() {
        val result = translateToSwift("fun main() {}")
        val expected = "public func main() -> Swift.Void"
        assertContains(result.swiftCode, expected)
    }
}