package ru.appngo.tankstutorial

import android.view.View
import android.widget.FrameLayout

fun View.moveTo(margin: Margin) {
    val layoutParams = (this.layoutParams as FrameLayout.LayoutParams)
    layoutParams.topMargin -= margin.top
    layoutParams.topMargin += margin.bottom
    layoutParams.leftMargin -= margin.left
    layoutParams.leftMargin += margin.right
}

fun View.assignDimensions(dimensions: Dimensions) {
    (this.layoutParams as FrameLayout.LayoutParams).apply {
        width = dimensions.width
        height = dimensions.height
        requestLayout()
    }
}

