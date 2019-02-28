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
import ru.appngo.tankstutorial.drawers.BulletDrawer
import ru.appngo.tankstutorial.drawers.ElementsDrawer
import ru.appngo.tankstutorial.drawers.EnemyDrawer
import ru.appngo.tankstutorial.drawers.GridDrawer
import ru.appngo.tankstutorial.enums.Direction
import ru.appngo.tankstutorial.enums.Direction.*
import ru.appngo.tankstutorial.enums.Material.*
import ru.appngo.tankstutorial.models.Coordinate
import ru.appngo.tankstutorial.models.Element
import ru.appngo.tankstutorial.models.Tank

const val CELL_SIZE = 50
const val VERTICAL_CELL_AMOUNT = 38
const val HORIZONTAL_CELL_AMOUNT = 25
const val VERTICAL_MAX_SIZE = CELL_SIZE * VERTICAL_CELL_AMOUNT
const val HORIZONTAL_MAX_SIZE = CELL_SIZE * HORIZONTAL_CELL_AMOUNT
const val HALF_WIDTH_OF_CONTAINER = VERTICAL_MAX_SIZE / 2

class MainActivity : AppCompatActivity() {
    private var editMode = false
    private val playerTank by lazy {
        Tank(
            Element(
                material = PLAYER_TANK,
                coordinate = getPlayerTankCoordinate()
            ), UP, BulletDrawer(container, elementsDrawer.elementsOnContainer, enemyDrawer)
        )
    }

    private fun getPlayerTankCoordinate() = Coordinate(
        top = HORIZONTAL_MAX_SIZE - PLAYER_TANK.height * CELL_SIZE,
        left = HALF_WIDTH_OF_CONTAINER - 8 * CELL_SIZE
    )

    private val eagle = Element(
        material = EAGLE,
        coordinate = getEagleCoordinate()
    )


    private fun getEagleCoordinate() = Coordinate(
        top = HORIZONTAL_MAX_SIZE - EAGLE.height * CELL_SIZE,
        left = HALF_WIDTH_OF_CONTAINER - EAGLE.width * CELL_SIZE / 2
    )

    private val gridDrawer by lazy {
        GridDrawer(container)
    }

    private val elementsDrawer by lazy {
        ElementsDrawer(container)
    }

    private val levelStorage by lazy {
        LevelStorage(this)
    }

    private val enemyDrawer by lazy {
        EnemyDrawer(container, elementsDrawer.elementsOnContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container.layoutParams = FrameLayout.LayoutParams(VERTICAL_MAX_SIZE, HORIZONTAL_MAX_SIZE)
        editor_clear.setOnClickListener { elementsDrawer.currentMaterial = EMPTY }
        editor_brick.setOnClickListener { elementsDrawer.currentMaterial = BRICK }
        editor_concrete.setOnClickListener { elementsDrawer.currentMaterial = CONCRETE }
        editor_grass.setOnClickListener { elementsDrawer.currentMaterial = GRASS }
        container.setOnTouchListener { _, event ->
            if (!editMode) {
                return@setOnTouchListener true
            }
            elementsDrawer.onTouchContainer(event.x, event.y)
            return@setOnTouchListener true
        }
        elementsDrawer.drawElementsList(levelStorage.loadLevel())
        elementsDrawer.drawElementsList(listOf(playerTank.element, eagle))
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
        enemyDrawer.startEnemyCreation()
        enemyDrawer.moveEnemyTanks()
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
            KEYCODE_DPAD_UP -> move(UP)
            KEYCODE_DPAD_LEFT -> move(LEFT)
            KEYCODE_DPAD_DOWN -> move(BOTTOM)
            KEYCODE_DPAD_RIGHT -> move(RIGHT)
            KEYCODE_SPACE -> playerTank.bulletDrawer.makeBulletMove(playerTank)
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun move(direction: Direction) {
        playerTank.move(direction, container, elementsDrawer.elementsOnContainer)
    }
}
