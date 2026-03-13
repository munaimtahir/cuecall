package com.cuecall.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityNavigationTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun launchesIntoModeSelection() {
        composeRule.onNodeWithText("Select Mode").assertIsDisplayed()
        composeRule.onNodeWithText("Reception").assertIsDisplayed()
        composeRule.onNodeWithText("Counter").assertIsDisplayed()
        composeRule.onNodeWithText("Display Board").assertIsDisplayed()
    }

    @Test
    fun settingsNavigationReturnsToModeSelection() {
        composeRule.onNodeWithContentDescription("Settings").performClick()

        composeRule.onNodeWithText("Settings").assertIsDisplayed()
        composeRule.onNodeWithText("Services").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Back").performClick()
        composeRule.onNodeWithText("Select Mode").assertIsDisplayed()
    }

    @Test
    fun receptionModeCardOpensReceptionScreen() {
        composeRule.onNodeWithText("Reception").performClick()

        composeRule.onNodeWithText("Reception").assertIsDisplayed()
        composeRule.onNodeWithText("Select Service").assertIsDisplayed()
    }

    @Test
    fun counterModeCardOpensCounterScreen() {
        composeRule.onNodeWithText("Counter").performClick()

        composeRule.onNodeWithText("Counter").assertIsDisplayed()
        composeRule.onNodeWithText("No Active Token").assertIsDisplayed()
    }
}
