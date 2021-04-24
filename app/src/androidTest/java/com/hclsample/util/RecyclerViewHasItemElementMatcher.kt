package com.hclsample.util

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Matcher

/**
 * A [<] that matches a [RecyclerView] if it contains a specified element (view) in an item
 * view at the given position.
 */
class RecyclerViewHasItemElementMatcher private constructor(
    recyclerViewId: Int,
    recyclerViewItemElementId: Int,
    recyclerViewItemPosition: Int
) : RecyclerViewItemElementMatcher(
    recyclerViewId,
    recyclerViewItemElementId,
    recyclerViewItemPosition
) {
    public override fun matches(
        item: View,
        itemView: View
    ): Boolean {
        return itemView.findViewById<View?>(recyclerViewItemElementId) != null
    }

    companion object {
        fun recyclerViewContainingItemWithElement(
            recyclerViewItemPosition: Int,
            @IdRes recyclerViewId: Int,
            @IdRes recyclerViewItemElementId: Int
        ): Matcher<View> {
            return RecyclerViewHasItemElementMatcher(
                recyclerViewId,
                recyclerViewItemElementId,
                recyclerViewItemPosition
            )
        }
    }
}