package pl.org.seva.licznik.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CounterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun counterScreen_displaysInitialCount() {
        composeTestRule.setContent {
            LicznikTheme {
                CounterScreen(
                    count = 7,
                    onIncrement = {},
                    onReset = {},
                )
            }
        }

        composeTestRule.onNodeWithText("7").assertIsDisplayed()
    }

    @Test
    fun counterScreen_clickingButtonIncrementsCount() {
        var count by mutableIntStateOf(0)

        composeTestRule.setContent {
            LicznikTheme {
                CounterScreen(
                    count = count,
                    onIncrement = { count++ },
                    onReset = { count = 0 },
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Licznik").performClick()

        assertEquals(1, count)
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
    }

    @Test
    fun counterScreen_resetMenuItemResetsCount() {
        var count by mutableIntStateOf(5)

        composeTestRule.setContent {
            LicznikTheme {
                CounterScreen(
                    count = count,
                    onIncrement = { count++ },
                    onReset = { count = 0 },
                )
            }
        }

        composeTestRule.onNodeWithText("⋮").performClick()
        composeTestRule.onNodeWithText("Reset").performClick()

        assertEquals(0, count)
        composeTestRule.onNodeWithText("0").assertIsDisplayed()
    }
}
