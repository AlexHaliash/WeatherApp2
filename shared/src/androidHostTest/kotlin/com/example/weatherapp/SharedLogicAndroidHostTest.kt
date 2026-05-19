package com.example.weatherapp

import kotlin.test.Test
import kotlin.test.assertEquals

class SharedLogicAndroidHostTest {

    @Test
    fun platformKind_isAndroid() {
        assertEquals(PlatformKind.Android, currentPlatformKind)
    }
}
