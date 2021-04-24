package com.hclsample.util

import android.view.View
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

/**
 * This is a custom class that implements Expresso interface ViewAction
 * in order to actually retrieve the text from a view
 */
class GetTextAction : ViewAction {
    var text: CharSequence? = null
        private set

    override fun getConstraints(): Matcher<View> {
        return ViewMatchers.isAssignableFrom(TextView::class.java)
    }

    override fun getDescription(): String {
        return "get text"
    }

    override fun perform(
        uiController: UiController,
        view: View
    ) {
        val textView = view as TextView
        text = textView.text
    }

}