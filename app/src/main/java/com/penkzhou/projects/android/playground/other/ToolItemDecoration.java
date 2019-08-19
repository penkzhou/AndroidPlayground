package com.penkzhou.projects.android.playground.other;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToolItemDecoration extends RecyclerView.ItemDecoration  {
    private final int gridSpacingInDp;
    private final int gridSize;
    private boolean needLeftSpacing = false;

    public ToolItemDecoration(int gridSpacingInDp, int gridSize) {
        this.gridSpacingInDp = gridSpacingInDp;
        this.gridSize = gridSize;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int frameWidth = (int) ((parent.getWidth() - (float) gridSpacingInDp * (gridSize - 1)) / gridSize);
        int padding = parent.getWidth() / gridSize - frameWidth;
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        if (itemPosition < gridSize) {
            outRect.top = 0;
        } else {
            outRect.top = gridSpacingInDp;
        }
        if (itemPosition % gridSize == 0) {
            outRect.left = 0;
            outRect.right = padding;
            needLeftSpacing = true;
        } else if ((itemPosition + 1) % gridSize == 0) {
            needLeftSpacing = false;
            outRect.right = 0;
            outRect.left = padding;
        } else if (needLeftSpacing) {
            needLeftSpacing = false;
            outRect.left = gridSpacingInDp - padding;
            if ((itemPosition + 2) % gridSize == 0) {
                outRect.right = gridSpacingInDp - padding;
            } else {
                outRect.right = gridSpacingInDp / 2;
            }
        } else if ((itemPosition + 2) % gridSize == 0) {
            needLeftSpacing = false;
            outRect.left = gridSpacingInDp / 2;
            outRect.right = gridSpacingInDp - padding;
        } else {
            needLeftSpacing = false;
            outRect.left = gridSpacingInDp / 2;
            outRect.right = gridSpacingInDp / 2;
        }
        outRect.bottom = 0;
    }
}
