package com.ruslan.assignment.view.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * Spacing decorator for [RecyclerView]
 */
class SpacingItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = space

        // for the first element we want top margin
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space
        }
    }
}