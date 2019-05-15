package ru.appngo.tankstutorial

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val gridDrawer by lazy {
        GridDrawer(container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container.assignDimensions(Dimensions(getContainerSide(this)))
        myTank.assignDimensions(Dimensions(getTankSide(this)))
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
        val layoutParams = myTank.layoutParams as FrameLayout.LayoutParams
        when (keyCode) {
            KEYCODE_DPAD_UP -> {
                myTank.rotation = 0f
                if (layoutParams.topMargin > 0) {
                    myTank.moveTo(Margin(top = getCellSize(this)))
                }
            }
            KEYCODE_DPAD_LEFT -> {
                myTank.rotation = 270f
                if (layoutParams.leftMargin > 0) {
                    myTank.moveTo(Margin(left = getCellSize(this)))
                }
            }
            KEYCODE_DPAD_DOWN -> {
                myTank.rotation = 180f
                if (layoutParams.topMargin + myTank.height < getContainerSide(this)) {
                    myTank.moveTo(Margin(bottom = getCellSize(this)))
                }
            }
            KEYCODE_DPAD_RIGHT -> {
                myTank.rotation = 90f
                if (layoutParams.leftMargin + myTank.width < getContainerSide(this)) {
                    myTank.moveTo(Margin(right = getCellSize(this)))
                }
            }
        }
        myTank.requestLayout()
        return super.onKeyDown(keyCode, event)
    }
}
