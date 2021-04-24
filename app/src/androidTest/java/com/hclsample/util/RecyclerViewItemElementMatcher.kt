package com.hclsample.util

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * A [<] that matches a specific element (view) inside of a [RecyclerView] item at the given
 * position.
 */
open class RecyclerViewItemElementMatcher internal constructor(
    recyclerViewId: Int,
    @field:IdRes val recyclerViewItemElementId: Int,
    recyclerViewItemPosition: Int
) : RecyclerViewItemMatcher(recyclerViewId, recyclerViewItemPosition) {
    override fun describeTo(description: Description) {
        super.describeTo(description)
        description.appendText(", with recycler view item element id: $recyclerViewItemElementId")
    }

    public override fun matches(
        item: View,
        itemView: View
    ): Boolean {
        return item === itemView.findViewById<View>(recyclerViewItemElementId)
    }

    companion object {
        fun withItemViewElement(
            recyclerViewItemPosition: Int, @IdRes recyclerViewId: Int,
            @IdRes recyclerViewItemElementId: Int
        ): Matcher<View> {
            return RecyclerViewItemElementMatcher(
                recyclerViewId,
                recyclerViewItemElementId,
                recyclerViewItemPosition
            )
        }
    }

}