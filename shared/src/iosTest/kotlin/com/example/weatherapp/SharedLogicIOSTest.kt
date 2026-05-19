package com.example.weatherapp

import kotlin.test.Test
import kotlin.test.assertEquals

class SharedLogicIOSTest {

    @Test
    fun platformKind_isIos() {
        assertEquals(PlatformKind.Ios, currentPlatformKind)
    }
}
