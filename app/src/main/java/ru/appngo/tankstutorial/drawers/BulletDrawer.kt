package ru.appngo.tankstutorial.drawers

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import ru.appngo.tankstutorial.CELL_SIZE
import ru.appngo.tankstutorial.R
import ru.appngo.tankstutorial.enums.Direction
import ru.appngo.tankstutorial.models.Coordinate
import ru.appngo.tankstutorial.models.Element
import ru.appngo.tankstutorial.utils.checkViewCanMoveThroughBorder
import ru.appngo.tankstutorial.utils.getElementByCoordinates
import ru.appngo.tankstutorial.utils.runOnUiThread

private const val BULLET_WIDTH = 15
private const val BULLET_HEIGHT = 25

class BulletDrawer(private val container: FrameLayout) {

    private var canBulletGoFurther = true
    private var bulletThread: Thread? = null

    private fun checkBulletThreadAlive() = bulletThread != null && bulletThread!!.isAlive

    fun makeBulletMove(myTank: View, currentDirection: Direction, elementsOnContainer: MutableList<Element>) {
        canBulletGoFurther = true
        if (!checkBulletThreadAlive()) {
            bulletThread = Thread(Runnable {
                val bullet = createBullet(myTank, currentDirection)
                while (bullet.checkViewCanMoveThroughBorder(Coordinate(bullet.top, bullet.left)) && canBulletGoFurther) {
                    when (currentDirection) {
                        Direction.UP -> (bullet.layoutParams as FrameLayout.LayoutParams).topMargin -= BULLET_HEIGHT
                        Direction.BOTTOM -> (bullet.layoutParams as FrameLayout.LayoutParams).topMargin += BULLET_HEIGHT
                        Direction.LEFT -> (bullet.layoutParams as FrameLayout.LayoutParams).leftMargin -= BULLET_HEIGHT
                        Direction.RIGHT -> (bullet.layoutParams as FrameLayout.LayoutParams).leftMargin += BULLET_HEIGHT
                    }
                    Thread.sleep(30)
                    chooseBehaviorInTermsOfDirections(
                            elementsOnContainer,
                            currentDirection,
                            Coordinate(
                                    (bullet.layoutParams as FrameLayout.LayoutParams).topMargin,
                                    (bullet.layoutParams as FrameLayout.LayoutParams).leftMargin))
                    container.runOnUiThread {
                        container.removeView(bullet)
                        container.addView(bullet)
                    }
                }
                container.runOnUiThread {
                    container.removeView(bullet)
                }
            })
            bulletThread!!.start()
        }
    }

    private fun chooseBehaviorInTermsOfDirections(
            elementsOnContainer: MutableList<Element>,
            currentDirection: Direction,
            bulletCoordinate: Coordinate
    ) {
        when (currentDirection) {
            Direction.BOTTOM, Direction.UP -> {
                compareCollections(elementsOnContainer, getCoordinatesForTopOrBottomDirection(bulletCoordinate))
            }
            Direction.LEFT, Direction.RIGHT -> {
                compareCollections(elementsOnContainer, getCoordinatesForLeftOrRightDirection(bulletCoordinate))
            }
        }
    }

    private fun compareCollections(
            elementsOnContainer: MutableList<Element>,
            detectedCoordinatesList: List<Coordinate>
    ) {
        detectedCoordinatesList.forEach {
            val element = getElementByCoordinates(it, elementsOnContainer)
            removeElementsAndStopBullet(element, elementsOnContainer)
        }
    }

    private fun removeElementsAndStopBullet(element: Element?, elementsOnContainer: MutableList<Element>) {
        if (element != null) {
            if (element.material.bulletCanGoThrough) {
                return
            }
            if (element.material.simpleBulletCanDestroy) {
                stopBullet()
                removeView(element)
                elementsOnContainer.remove(element)
            } else {
                stopBullet()
            }
        }
    }

    private fun stopBullet() {
        canBulletGoFurther = false
    }

    private fun removeView(element: Element) {
        val activity = container.context as Activity
        activity.runOnUiThread {
            container.removeView(activity.findViewById(element.viewId))
        }
    }

    private fun getCoordinatesForTopOrBottomDirection(bulletCoordinate: Coordinate): List<Coordinate> {
        val leftCell = bulletCoordinate.left - bulletCoordinate.left % CELL_SIZE
        val rightCell = leftCell + CELL_SIZE
        val topCoordinate = bulletCoordinate.top - bulletCoordinate.top % CELL_SIZE
        return listOf(
                Coordinate(topCoordinate, leftCell),
                Coordinate(topCoordinate, rightCell))
    }

    private fun getCoordinatesForLeftOrRightDirection(bulletCoordinate: Coordinate): List<Coordinate> {
        val topCell = bulletCoordinate.top - bulletCoordinate.top % CELL_SIZE
        val bottomCell = topCell + CELL_SIZE
        val leftCoordinate = bulletCoordinate.left - bulletCoordinate.left % CELL_SIZE
        return listOf(
                Coordinate(topCell, leftCoordinate),
                Coordinate(bottomCell, leftCoordinate))
    }

    private fun createBullet(myTank: View, currentDirection: Direction): ImageView {
        return ImageView(container.context)
                .apply {
                    this.setImageResource(R.drawable.bullet)
                    this.layoutParams = FrameLayout.LayoutParams(BULLET_WIDTH, BULLET_HEIGHT)
                    val bulletCoordinate = getBulletCoordinates(this, myTank, currentDirection)
                    (this.layoutParams as FrameLayout.LayoutParams).topMargin = bulletCoordinate.top
                    (this.layoutParams as FrameLayout.LayoutParams).leftMargin = bulletCoordinate.left
                    this.rotation = currentDirection.rotation
                }
    }

    private fun getBulletCoordinates(
            bullet: ImageView,
            myTank: View,
            currentDirection: Direction
    ): Coordinate {
        val tankLeftTopCoordinate = Coordinate(myTank.top, myTank.left)
        return when (currentDirection) {
            Direction.UP ->
                Coordinate(
                        top = tankLeftTopCoordinate.top - bullet.layoutParams.height,
                        left = getDistanceToMiddleOfTank(tankLeftTopCoordinate.left, bullet.layoutParams.width))
            Direction.BOTTOM ->
                Coordinate(
                        top = tankLeftTopCoordinate.top + myTank.layoutParams.height,
                        left = getDistanceToMiddleOfTank(tankLeftTopCoordinate.left, bullet.layoutParams.width))
            Direction.LEFT ->
                Coordinate(
                        top = getDistanceToMiddleOfTank(tankLeftTopCoordinate.top, bullet.layoutParams.height),
                        left = tankLeftTopCoordinate.left - bullet.layoutParams.width)
            Direction.RIGHT ->
                Coordinate(
                        top = getDistanceToMiddleOfTank(tankLeftTopCoordinate.top, bullet.layoutParams.height),
                        left = tankLeftTopCoordinate.left + myTank.layoutParams.width)
        }
    }

    private fun getDistanceToMiddleOfTank(startCoordinate: Int, bulletSize: Int): Int {
        return startCoordinate + (CELL_SIZE - bulletSize / 2)
    }
}
