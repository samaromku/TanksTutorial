package ru.appngo.tankstutorial

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.FrameLayout

class GridDrawer(private val context: Context) {

    private val allLines = mutableListOf<View>()

    fun removeGrid() {
        val container = (context as Activity).findViewById<FrameLayout>(R.id.container)
        allLines.forEach {
            container.removeView(it)
        }
    }

    fun drawGrid() {
        val container = (context as Activity).findViewById<FrameLayout>(R.id.container)
        drawHorizontalLines(container)
        drawVerticalLines(container)
    }

    private fun drawHorizontalLines(container: FrameLayout) {
        var topMargin = 0
        while (topMargin <= container.layoutParams.height) {
            val horizontalLine = View(context)
            val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 1)
            topMargin += CELL_SIZE
            layoutParams.topMargin = topMargin
            horizontalLine.layoutParams = layoutParams
            horizontalLine.setBackgroundColor(context.resources.getColor(android.R.color.white))
            allLines.add(horizontalLine)
            container.addView(horizontalLine)
        }
    }

    private fun drawVerticalLines(container: FrameLayout) {
        var leftMargin = 0
        while (leftMargin <= container.layoutParams.width) {
            val verticalLine = View(context)
            val layoutParams = FrameLayout.LayoutParams(1, FrameLayout.LayoutParams.MATCH_PARENT)
            leftMargin += CELL_SIZE
            layoutParams.leftMargin = leftMargin
            verticalLine.layoutParams = layoutParams
            verticalLine.setBackgroundColor(context.resources.getColor(android.R.color.white))
            allLines.add(verticalLine)
            container.addView(verticalLine)
        }
    }

}
