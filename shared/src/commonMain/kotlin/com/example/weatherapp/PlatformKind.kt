package com.example.weatherapp

enum class PlatformKind {
    Android,
    Ios,
    Desktop,
    Web,
}

expect val currentPlatformKind: PlatformKind
