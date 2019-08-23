package com.penkzhou.projects.android.playground.utils

import com.penkzhou.projects.android.playground.PlaygroundApplication

object Utils {
    fun dpToPx(dp: Int): Int {
        val scale = PlaygroundApplication.appContext!!.getResources().getDisplayMetrics().density
        // Convert the dps to pixels, based on density scale
        return (dp * scale + 0.5f).toInt()
    }

}
