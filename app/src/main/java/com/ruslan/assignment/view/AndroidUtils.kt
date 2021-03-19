package com.ruslan.assignment.view

import android.content.Context
import android.util.DisplayMetrics

/**
 * Converts dp to pixels
 */
fun Context.convertDpToPx(dp: Float): Int {
    return ((dp * (this.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).toInt())
}