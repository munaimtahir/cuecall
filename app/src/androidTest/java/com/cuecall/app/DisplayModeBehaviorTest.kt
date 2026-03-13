package com.cuecall.app

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DisplayModeBehaviorTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun backPressInDisplayModeShowsExitDialogAndStayDismissesIt() {
        composeRule.onNodeWithText("Display Board").performClick()

        composeRule.activityRule.scenario.onActivity {
            it.onBackPressedDispatcher.onBackPressed()
        }

        composeRule.onNodeWithText("Exit Display Mode?").assertIsDisplayed()
        composeRule.onNodeWithText("Stay").performClick()
        composeRule.onAllNodesWithText("Exit Display Mode?").assertCountEquals(0)
    }

    @Test
    fun exitFromDisplayModeReturnsToModeSelection() {
        composeRule.onNodeWithText("Display Board").performClick()

        composeRule.activityRule.scenario.onActivity {
            it.onBackPressedDispatcher.onBackPressed()
        }

        composeRule.onNodeWithText("Exit").performClick()
        composeRule.onNodeWithText("Select Mode").assertIsDisplayed()
    }
}
