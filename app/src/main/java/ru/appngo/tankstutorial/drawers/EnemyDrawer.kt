package ru.appngo.tankstutorial.drawers

import android.widget.FrameLayout
import ru.appngo.tankstutorial.CELL_SIZE
import ru.appngo.tankstutorial.HALF_WIDTH_OF_CONTAINER
import ru.appngo.tankstutorial.VERTICAL_MAX_SIZE
import ru.appngo.tankstutorial.enums.Direction.BOTTOM
import ru.appngo.tankstutorial.enums.Material.ENEMY_TANK
import ru.appngo.tankstutorial.models.Coordinate
import ru.appngo.tankstutorial.models.Element
import ru.appngo.tankstutorial.models.Tank
import ru.appngo.tankstutorial.utils.checkIfChanceBiggerThanRandom
import ru.appngo.tankstutorial.utils.drawElement

private const val MAX_ENEMY_AMOUNT = 20

class EnemyDrawer(
    private val container: FrameLayout,
    private val elements: MutableList<Element>
) {
    private val respawnList: List<Coordinate>
    private var enemyAmount = 0
    private var currentCoordinate: Coordinate
    val tanks = mutableListOf<Tank>()
    private var moveAllTanksThread: Thread? = null
    lateinit var bulletDrawer: BulletDrawer

    init {
        respawnList = getRespawnList()
        currentCoordinate = respawnList[0]
    }

    private fun getRespawnList(): List<Coordinate> {
        val respawnList = mutableListOf<Coordinate>()
        respawnList.add(Coordinate(0, 0))
        respawnList.add(Coordinate(0, HALF_WIDTH_OF_CONTAINER - CELL_SIZE))
        respawnList.add(Coordinate(0, VERTICAL_MAX_SIZE - 2 * CELL_SIZE))
        return respawnList
    }

    fun startEnemyCreation() {
        Thread(Runnable {
            while (enemyAmount < MAX_ENEMY_AMOUNT) {
                drawEnemy()
                enemyAmount++
                Thread.sleep(3000)
            }
        }).start()
    }

    private fun drawEnemy() {
        var index = respawnList.indexOf(currentCoordinate) + 1
        if (index == respawnList.size) {
            index = 0
        }
        currentCoordinate = respawnList[index]
        val enemyTank = Tank(
            Element(
                material = ENEMY_TANK,
                coordinate = currentCoordinate
            ), BOTTOM, this
        )
        enemyTank.element.drawElement(container)
        tanks.add(enemyTank)
    }

    fun moveEnemyTanks() {
        Thread(Runnable {
            while (true) {
                goThroughAllTanks()
                Thread.sleep(400)
            }
        }).start()
    }

    private fun goThroughAllTanks() {
        moveAllTanksThread = Thread(Runnable {
            tanks.forEach {
                it.move(it.direction, container, elements)
                if (checkIfChanceBiggerThanRandom(10)) {
                    bulletDrawer.addNewBulletForTank(it)
                }
            }
        })
        moveAllTanksThread?.start()
    }

    fun removeTank(tankIndex: Int) {
        if (tankIndex < 0) return
        moveAllTanksThread?.join()
        tanks.removeAt(tankIndex)
    }
}
