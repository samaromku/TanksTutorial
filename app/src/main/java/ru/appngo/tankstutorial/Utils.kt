package ru.appngo.tankstutorial

import android.app.Activity
import android.content.Context
import android.support.annotation.LayoutRes
import android.util.DisplayMetrics
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

const val NUMBER_OF_CELLS_IN_SQUARE_SIDE = 26
const val TOP_BOTTOM_MARGINS_FOR_GAME_FIELD = 192
const val LEFT_RIGHT_MARGINS_FOR_GAME_FIELD = 16

fun getContainerSide(context: Context) = getCellSize(context) * NUMBER_OF_CELLS_IN_SQUARE_SIDE

fun getCellSize(context: Context): Int {
    var cellSize: Int
    DisplayMetrics().apply {
        (context as Activity).windowManager.defaultDisplay.getMetrics(this)
        val minSideSize = Math.min(heightPixels, widthPixels)
        cellSize = if (minSideSize == heightPixels) {
            (minSideSize - TOP_BOTTOM_MARGINS_FOR_GAME_FIELD - (minSideSize % NUMBER_OF_CELLS_IN_SQUARE_SIDE)) / NUMBER_OF_CELLS_IN_SQUARE_SIDE
        } else {
            (minSideSize - LEFT_RIGHT_MARGINS_FOR_GAME_FIELD - (minSideSize % NUMBER_OF_CELLS_IN_SQUARE_SIDE)) / NUMBER_OF_CELLS_IN_SQUARE_SIDE
        }
    }
    require(cellSize != 0)
    return cellSize
}

const val TANK_SIDE_CELLS = 2

fun getTankSide(context: Context) = getCellSize(context) * TANK_SIDE_CELLS

fun View.assignDimensions(dimensions: Dimensions) {
    (this.layoutParams as FrameLayout.LayoutParams).apply {
        width = dimensions.width
        height = dimensions.height
    }
}

