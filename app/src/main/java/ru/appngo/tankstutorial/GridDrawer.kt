package ru.appngo.tankstutorial

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.FrameLayout

class GridDrawer(private val context: Context) {
    fun drawGrid(){
        val container = (context as Activity).findViewById<FrameLayout>(R.id.container)
        drawHorizontalLines(container)
        drawVerticalLines(container)
    }

    private fun drawHorizontalLines(container: FrameLayout) {
        var topMargin = 0
        while (topMargin <= container.layoutParams.height){
            val horizontalLine = View(context)
            val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 1)
            topMargin += CELL_SIZE
            layoutParams.topMargin = topMargin
            horizontalLine.layoutParams = layoutParams
            horizontalLine.setBackgroundColor(context.resources.getColor(android.R.color.white))
            container.addView(horizontalLine)
        }
    }

    private fun drawVerticalLines(container: FrameLayout) {
        var leftMargin = 0
        while (leftMargin <= container.layoutParams.width){
            val horizontalLine = View(context)
            val layoutParams = FrameLayout.LayoutParams(1, FrameLayout.LayoutParams.MATCH_PARENT)
            leftMargin += CELL_SIZE
            layoutParams.leftMargin = leftMargin
            horizontalLine.layoutParams = layoutParams
            horizontalLine.setBackgroundColor(context.resources.getColor(android.R.color.white))
            container.addView(horizontalLine)
        }
    }

}
