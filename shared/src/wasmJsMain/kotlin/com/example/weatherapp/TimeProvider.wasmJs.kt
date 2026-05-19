package com.example.weatherapp

import kotlin.js.Date

internal actual fun currentTimeMillis(): Long = Date.now().toLong()
