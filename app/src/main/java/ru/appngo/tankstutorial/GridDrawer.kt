package ru.appngo.tankstutorial

import android.widget.FrameLayout

class GridDrawer(private val container: FrameLayout, private val cellSize: Int) {

    fun drawGrid() {
        drawHorizontalLines()
        drawVerticalLines()
    }

    private fun drawHorizontalLines() {
        var margin = 0
        while (margin <= container.layoutParams.height - cellSize) {
            margin += cellSize
            container.createViewWithMarginAddToContainer(R.layout.horizontal_line, Margin(bottom = margin))
        }
    }

    private fun drawVerticalLines() {
        var margin = 0
        while (margin <= container.layoutParams.width - cellSize) {
            margin += cellSize
            container.createViewWithMarginAddToContainer(R.layout.vertical_line, Margin(right = margin))
        }
    }
}
