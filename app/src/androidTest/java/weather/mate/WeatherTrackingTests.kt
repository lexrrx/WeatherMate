package weather.mate

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import weather.mate.ui.components.HeaderControl

class WeatherTrackingTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun headerControlTextTest() {
        // Set the initial state
        val isTrackingEnabled = true

        composeTestRule.setContent {
            HeaderControl(isTrackingEnabled = isTrackingEnabled)
        }

        // Assert that the text changes based on isTrackingEnabled
        composeTestRule.onNodeWithText("Stop tracking me").assertIsDisplayed()
    }
}