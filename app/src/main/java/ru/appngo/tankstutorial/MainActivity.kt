package ru.appngo.tankstutorial

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.*
import ru.appngo.tankstutorial.drawers.*
import ru.appngo.tankstutorial.enums.Direction.*
import ru.appngo.tankstutorial.enums.Material

const val CELL_SIZE = 50
const val VERTICAL_CELL_AMOUNT = 38
const val HORIZONTAL_CELL_AMOUNT = 25
const val VERTICAL_MAX_SIZE = CELL_SIZE * VERTICAL_CELL_AMOUNT
const val HORIZONTAL_MAX_SIZE = CELL_SIZE * HORIZONTAL_CELL_AMOUNT

class MainActivity : AppCompatActivity() {
    private var editMode = false

    private val gridDrawer by lazy {
        GridDrawer(container)
    }

    private val elementsDrawer by lazy {
        ElementsDrawer(container)
    }

    private val tankDrawer by lazy {
        TankDrawer(container)
    }

    private val bulletDrawer by lazy {
        BulletDrawer(container)
    }

    private val levelStorage by lazy {
        LevelStorage(this)
    }

    private val enemyDrawer by lazy {
        EnemyDrawer(container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container.layoutParams = FrameLayout.LayoutParams(VERTICAL_MAX_SIZE, HORIZONTAL_MAX_SIZE)
        editor_clear.setOnClickListener { elementsDrawer.currentMaterial = Material.EMPTY }
        editor_brick.setOnClickListener { elementsDrawer.currentMaterial = Material.BRICK }
        editor_concrete.setOnClickListener { elementsDrawer.currentMaterial = Material.CONCRETE }
        editor_grass.setOnClickListener { elementsDrawer.currentMaterial = Material.GRASS }
        editor_eagle.setOnClickListener { elementsDrawer.currentMaterial = Material.EAGLE }
        container.setOnTouchListener { _, event ->
            elementsDrawer.onTouchContainer(event.x, event.y)
            return@setOnTouchListener true
        }
        elementsDrawer.drawElementsList(levelStorage.loadLevel())
        hideSettings()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                switchEditMode()
                true
            }
            R.id.menu_save -> {
                levelStorage.saveLevel(elementsDrawer.elementsOnContainer)
                true
            }
            R.id.menu_play -> {
                startTheGame()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startTheGame() {
        if (editMode) {
            return
        }
        enemyDrawer.startEnemyDrawing(elementsDrawer.elementsOnContainer)
    }

    private fun switchEditMode() {
        editMode = !editMode
        if (editMode) {
            showSettings()
        } else {
            hideSettings()
        }
    }

    private fun showSettings() {
        gridDrawer.drawGrid()
        materials_container.visibility = VISIBLE
    }

    private fun hideSettings() {
        gridDrawer.removeGrid()
        materials_container.visibility = GONE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KEYCODE_DPAD_UP -> tankDrawer.move(myTank, UP, elementsDrawer.elementsOnContainer)
            KEYCODE_DPAD_LEFT -> tankDrawer.move(myTank, LEFT, elementsDrawer.elementsOnContainer)
            KEYCODE_DPAD_DOWN -> tankDrawer.move(myTank, BOTTOM, elementsDrawer.elementsOnContainer)
            KEYCODE_DPAD_RIGHT -> tankDrawer.move(myTank, RIGHT, elementsDrawer.elementsOnContainer)
            KEYCODE_SPACE -> bulletDrawer.makeBulletMove(
                myTank,
                tankDrawer.currentDirection,
                elementsDrawer.elementsOnContainer
            )
        }
        return super.onKeyDown(keyCode, event)
    }
}
