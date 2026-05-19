package com.example.weatherapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun launch_showsWeatherTitle() {
        composeRule.onNodeWithText("Погода").assertIsDisplayed()
    }

    @Test
    fun launch_showsSearchButton() {
        composeRule.onNodeWithText("Узнать погоду").assertIsDisplayed()
    }

    @Test
    fun launch_showsCityFieldLabel() {
        composeRule.onNodeWithText("Название города").assertIsDisplayed()
    }

    @Test
    fun launch_showsSearchHint() {
        composeRule.onNodeWithText("Введите город", substring = true).assertIsDisplayed()
    }

    @Test
    fun launch_showsSearchIconEmoji() {
        composeRule.onNodeWithText("🔍").assertIsDisplayed()
    }
}
