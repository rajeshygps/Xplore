package com.hclsample.util

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher
import org.hamcrest.Matchers

/**
 * A [ViewAction] that waits for a given condition ([<] to be true.
 */
class WaitAction private constructor(private val condition: Matcher<View>) :
    ViewAction {
    override fun getConstraints(): Matcher<View> {
        return Matchers.any(View::class.java)
    }

    override fun getDescription(): String {
        return "waiting for condition: $condition"
    }

    override fun perform(
        uiController: UiController,
        view: View
    ) {
        val timeoutTime =
            System.currentTimeMillis() + TIMEOUT_MILLIS
        uiController.loopMainThreadUntilIdle()
        while (System.currentTimeMillis() < timeoutTime) {
            if (condition.matches(view)) {
                uiController.loopMainThreadForAtLeast(CONDITION_MATCH_DELAY)
                return
            }
            uiController.loopMainThreadForAtLeast(CONDITION_CHECK_INTERVAL_MILLIS)
        }
        throw PerformException.Builder()
            .withActionDescription("wait")
            .withViewDescription("with id: " + view.id)
            .build()
    }

    companion object {
        private const val TIMEOUT_MILLIS = 30000L
        private const val CONDITION_CHECK_INTERVAL_MILLIS = 200L

        // This delay is necessary as an action would most likely be performed on the object that the wait was used for.
        // That object (view) may not be immediately ready hence the reason for this delay.
        private const val CONDITION_MATCH_DELAY = 5000L
        fun waitFor(condition: Matcher<View>): ViewAction {
            return WaitAction(condition)
        }
    }

}