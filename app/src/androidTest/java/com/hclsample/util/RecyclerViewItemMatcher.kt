package com.hclsample.util

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**
 * A [<] that matches a [RecyclerView] item at the given position.
 */
open class RecyclerViewItemMatcher internal constructor(
    @field:IdRes private val recyclerViewId: Int,
    private val recyclerViewItemPosition: Int
) : TypeSafeMatcher<View>() {
    @CallSuper
    override fun describeTo(description: Description) {
        description.appendText("with recycler view id: $recyclerViewId")
            .appendText(", with recycler view item position: $recyclerViewItemPosition")
    }

    override fun matchesSafely(item: View): Boolean {
        val recyclerView =
            item.rootView.findViewById<View>(recyclerViewId) as RecyclerView
        var itemView: View? = null
        if (recyclerView != null && recyclerView.id == recyclerViewId) {
            val itemViewHolder =
                recyclerView.findViewHolderForAdapterPosition(recyclerViewItemPosition)
            if (itemViewHolder != null) {
                itemView = itemViewHolder.itemView
            }
        }
        return itemView != null && matches(item, itemView)
    }

    open fun matches(item: View, itemView: View): Boolean {
        return item === itemView
    }

    companion object {
        fun withItemViewAtPosition(
            recyclerViewItemPosition: Int,
            @IdRes recyclerViewId: Int
        ): Matcher<View> {
            return RecyclerViewItemMatcher(recyclerViewId, recyclerViewItemPosition)
        }
    }

}