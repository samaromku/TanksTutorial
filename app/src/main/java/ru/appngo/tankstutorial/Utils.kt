package ru.appngo.tankstutorial

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
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

fun FrameLayout.createViewWithMargin(@LayoutRes layout: Int, margin: Margin): View {
    return LayoutInflater.from(this.context).inflate(layout, this, false).apply {
        moveTo(margin)
    }
}

fun FrameLayout.createViewWithMarginAddToContainer(@LayoutRes layout: Int, margin: Margin): View {
    val view = createViewWithMargin(layout, margin)
    this.addView(view)
    return view
}

