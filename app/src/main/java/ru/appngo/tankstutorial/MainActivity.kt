package ru.appngo.tankstutorial

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.*
import ru.appngo.tankstutorial.Direction.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        when (direction) {
            UP -> {
                myTank.rotation = 0f
                (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += -50
            }
            BOTTOM -> {
                myTank.rotation = 180f
                (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += 50
            }
            RIGHT -> {
                myTank.rotation = 90f
                (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin += 50
            }
            LEFT -> {
                myTank.rotation = 270f
                (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin += -50
            }
        }
        container.removeView(myTank)
        container.addView(myTank)
    }
}
