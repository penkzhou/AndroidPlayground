package com.penkzhou.projects.android.playground.utils;

import com.penkzhou.projects.android.playground.PlaygroundApplication;

public final class Utils {
    public static int dpToPx(int dp) {
        final float scale = PlaygroundApplication.getAppContext().getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }

}
