package com.hclsample.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert

/**
 * A [ViewAssertion] that verifies the item count of a [RecyclerView].
 */
class RecyclerViewItemCountAssertion private constructor(private val itemCountMatcher: Matcher<Int>) :
    ViewAssertion {
    override fun check(view: View, nmve: NoMatchingViewException) {
        if (nmve != null) {
            throw nmve
        }
        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        MatcherAssert.assertThat(
            "RecyclerView should have the correct number of items",
            adapter!!.itemCount, itemCountMatcher
        )
    }

    companion object {
        fun hasItemCount(itemCountMatcher: Matcher<Int>): RecyclerViewItemCountAssertion {
            return RecyclerViewItemCountAssertion(itemCountMatcher)
        }
    }

}