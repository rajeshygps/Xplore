package com.hclsample.util

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Matcher
import org.hamcrest.Matchers

/**
 * Custom class that implements Espresso's ViewAction to provide custom scrolling behavior applicable to
 * descendants of Nested ScrollViews for UI tests.
 */
class WlScrollToAction : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return Matchers.allOf(
            ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
            ViewMatchers.isDescendantOfA(
                Matchers.anyOf(
                    ViewMatchers.isAssignableFrom(ScrollView::class.java),
                    ViewMatchers.isAssignableFrom(NestedScrollView::class.java),
                    ViewMatchers.isAssignableFrom(HorizontalScrollView::class.java),
                    ViewMatchers.isAssignableFrom(ListView::class.java)
                )
            )
        )
    }

    override fun getDescription(): String {
        return "scroll to"
    }

    override fun perform(
        uiController: UiController,
        view: View
    ) {
        if (ViewMatchers.isDisplayingAtLeast(90).matches(view)) {
            Log.i(
                TAG,
                "View is already displayed. Returning."
            )
            return
        }
        val rect = Rect()
        view.getDrawingRect(rect)
        if (!view.requestRectangleOnScreen(rect, true /* immediate */)) {
            Log.w(
                TAG,
                "Scrolling to view was requested, but none of the parents scrolled."
            )
        }
        uiController.loopMainThreadUntilIdle()
        if (!ViewMatchers.isDisplayingAtLeast(90).matches(view)) {
            throw PerformException.Builder()
                .withActionDescription(this.description)
                .withViewDescription(HumanReadables.describe(view))
                .withCause(
                    RuntimeException(
                        "Scrolling to view was attempted, but the view is not displayed"
                    )
                )
                .build()
        }
    }

    companion object {
        private val TAG = WlScrollToAction::class.java.simpleName
    }
}