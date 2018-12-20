package ru.appngo.tankstutorial

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_DPAD_DOWN
import android.view.KeyEvent.KEYCODE_DPAD_LEFT
import android.view.KeyEvent.KEYCODE_DPAD_RIGHT
import android.view.KeyEvent.KEYCODE_DPAD_UP
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.*
import ru.appngo.tankstutorial.Direction.BOTTOM
import ru.appngo.tankstutorial.Direction.LEFT
import ru.appngo.tankstutorial.Direction.RIGHT
import ru.appngo.tankstutorial.Direction.UP

const val CELL_SIZE = 50
const val VERTICAL_CELL_AMOUNT = 38
const val HORIZONTAL_CELL_AMOUNT = 25
const val VERTICAL_MAX_SIZE = CELL_SIZE * VERTICAL_CELL_AMOUNT
const val HORIZONTAL_MAX_SIZE = CELL_SIZE * HORIZONTAL_CELL_AMOUNT

class MainActivity : AppCompatActivity() {
    private val gridDrawer by lazy {
        GridDrawer(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container.layoutParams = FrameLayout.LayoutParams(VERTICAL_MAX_SIZE, HORIZONTAL_MAX_SIZE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                gridDrawer.drawGrid()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KEYCODE_DPAD_UP -> move(UP)
            KEYCODE_DPAD_LEFT -> move(LEFT)
            KEYCODE_DPAD_DOWN -> move(BOTTOM)
            KEYCODE_DPAD_RIGHT -> move(RIGHT)
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun move(direction: Direction) {
        val layoutParams = myTank.layoutParams as FrameLayout.LayoutParams
        when (direction) {
            UP -> {
                myTank.rotation = 0f
                if (layoutParams.topMargin > 0) {
                    (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += -CELL_SIZE
                }
            }
            BOTTOM -> {
                myTank.rotation = 180f
                if (layoutParams.topMargin + myTank.height < HORIZONTAL_MAX_SIZE) {
                    (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += CELL_SIZE
                }
            }
            RIGHT -> {
                myTank.rotation = 90f
                if (layoutParams.leftMargin + myTank.width < VERTICAL_MAX_SIZE) {
                    (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin += CELL_SIZE
                }
            }
            LEFT -> {
                myTank.rotation = 270f
                if (layoutParams.leftMargin > 0) {
                    (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin += -CELL_SIZE
                }
            }
        }
        container.removeView(myTank)
        container.addView(myTank)
    }
}
