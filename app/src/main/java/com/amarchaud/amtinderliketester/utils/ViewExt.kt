package com.amarchaud.amtinderliketester.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat


/**
 * Allow to draw "behind" transparent statusBar
 */
fun Activity.setFullScreen() {
    window.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setDecorFitsSystemWindows(false)
        } else {
            val flags = decorView.systemUiVisibility
            decorView.systemUiVisibility = (flags or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }
    }
}

fun View.setTopBottomInsets(addInsets: (topInsets: Int, bottomInsets: Int) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            addInsets(
                insets.getInsets(WindowInsets.Type.statusBars()).top,
                insets.getInsets(WindowInsets.Type.navigationBars()).bottom
            )
        } else {
            addInsets(
                insets.systemWindowInsetTop,
                insets.systemWindowInsetBottom
            )
        }
        insets
    }
}

fun View.setTopPadding(top: Int) {
    this.setPadding(
        this.paddingLeft,
        top,
        this.paddingRight,
        this.paddingBottom
    )
}


fun View.setBottomPadding(bottom: Int) {
    this.setPadding(
        this.paddingLeft,
        this.paddingTop,
        this.paddingRight,
        bottom
    )
}

fun View.setBottomMargin(bottomInsets: Int) {
    (this.layoutParams as ConstraintLayout.LayoutParams).bottomMargin = bottomInsets
}

fun convertDpToPixel(dp: Float, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun View.convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}