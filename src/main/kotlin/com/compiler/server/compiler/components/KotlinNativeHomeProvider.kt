package com.compiler.server.compiler.components

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class KotlinNativeHomeProvider(
    @Value("\${kotlin.native.home}") val kotlinNativeHome: String
)