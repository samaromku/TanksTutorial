package ru.appngo.tankstutorial

import android.widget.FrameLayout

class GridDrawer(private val container: FrameLayout) {

    fun drawGrid() {
        drawHorizontalLines(container)
        drawVerticalLines(container)
    }

    private fun drawHorizontalLines(container: FrameLayout) {
        var margin = 0
        while (margin <= container.layoutParams.height - getCellSize(container.context)) {
            margin += getCellSize(container.context)
            container.createViewWithMarginAddToContainer(R.layout.horizontal_line, Margin(bottom = margin))
        }
    }

    private fun drawVerticalLines(container: FrameLayout) {
        var margin = 0
        while (margin <= container.layoutParams.width - getCellSize(container.context)) {
            margin += getCellSize(container.context)
            container.createViewWithMarginAddToContainer(R.layout.vertical_line, Margin(right = margin))
        }
    }
}
