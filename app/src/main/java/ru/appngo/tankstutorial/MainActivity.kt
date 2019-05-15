package ru.appngo.tankstutorial

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.*

const val NUMBER_OF_CELLS_IN_SQUARE_SIDE = 26
const val TANK_SIDE_CELLS = 2

class MainActivity : AppCompatActivity() {

    private var cellSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        cellSize = getCellSize(container)
        container.assignDimensions(Dimensions(getContainerSide()))
        myTank.assignDimensions(Dimensions(getTankSide()))
    }

    private fun getCellSize(container: FrameLayout): Int {
        val width = container.width
        val height = container.height
        val minDimension = Math.min(width, height)
        return (minDimension - (minDimension % NUMBER_OF_CELLS_IN_SQUARE_SIDE)) / NUMBER_OF_CELLS_IN_SQUARE_SIDE
    }

    private fun getContainerSide() = cellSize * NUMBER_OF_CELLS_IN_SQUARE_SIDE

    private fun getTankSide() = cellSize * TANK_SIDE_CELLS


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val layoutParams = myTank.layoutParams as FrameLayout.LayoutParams
        when (keyCode) {
            KEYCODE_DPAD_UP -> {
                myTank.rotation = 0f
                if (layoutParams.topMargin > 0) {
                    myTank.moveTo(Margin(top = cellSize))
                }
            }
            KEYCODE_DPAD_LEFT -> {
                myTank.rotation = 270f
                if (layoutParams.leftMargin > 0) {
                    myTank.moveTo(Margin(left = cellSize))
                }
            }
            KEYCODE_DPAD_DOWN -> {
                myTank.rotation = 180f
                if (layoutParams.topMargin + myTank.height < getContainerSide()) {
                    myTank.moveTo(Margin(bottom = cellSize))
                }
            }
            KEYCODE_DPAD_RIGHT -> {
                myTank.rotation = 90f
                if (layoutParams.leftMargin + myTank.width < getContainerSide()) {
                    myTank.moveTo(Margin(right = cellSize))
                }
            }
        }
        myTank.requestLayout()
        return super.onKeyDown(keyCode, event)
    }
}
