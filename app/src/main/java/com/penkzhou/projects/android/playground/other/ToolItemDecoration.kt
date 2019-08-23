package com.penkzhou.projects.android.playground.other

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ToolItemDecoration(private val gridSpacingInDp: Int, private val gridSize: Int) : RecyclerView.ItemDecoration() {
    private var needLeftSpacing = false

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val frameWidth = ((parent.width - gridSpacingInDp.toFloat() * (gridSize - 1)) / gridSize).toInt()
        val padding = parent.width / gridSize - frameWidth
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        if (itemPosition < gridSize) {
            outRect.top = 0
        } else {
            outRect.top = gridSpacingInDp
        }
        if (itemPosition % gridSize == 0) {
            outRect.left = 0
            outRect.right = padding
            needLeftSpacing = true
        } else if ((itemPosition + 1) % gridSize == 0) {
            needLeftSpacing = false
            outRect.right = 0
            outRect.left = padding
        } else if (needLeftSpacing) {
            needLeftSpacing = false
            outRect.left = gridSpacingInDp - padding
            if ((itemPosition + 2) % gridSize == 0) {
                outRect.right = gridSpacingInDp - padding
            } else {
                outRect.right = gridSpacingInDp / 2
            }
        } else if ((itemPosition + 2) % gridSize == 0) {
            needLeftSpacing = false
            outRect.left = gridSpacingInDp / 2
            outRect.right = gridSpacingInDp - padding
        } else {
            needLeftSpacing = false
            outRect.left = gridSpacingInDp / 2
            outRect.right = gridSpacingInDp / 2
        }
        outRect.bottom = 0
    }
}
