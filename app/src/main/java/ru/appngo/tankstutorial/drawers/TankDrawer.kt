package ru.appngo.tankstutorial.drawers

import android.view.View
import android.widget.FrameLayout
import ru.appngo.tankstutorial.CELL_SIZE
import ru.appngo.tankstutorial.HORIZONTAL_MAX_SIZE
import ru.appngo.tankstutorial.VERTICAL_MAX_SIZE
import ru.appngo.tankstutorial.enums.Direction
import ru.appngo.tankstutorial.models.Coordinate
import ru.appngo.tankstutorial.models.Element
import ru.appngo.tankstutorial.utils.checkViewCanMoveThroughBorder

class TankDrawer(val container: FrameLayout) {
    var currentDirection = Direction.BOTTOM

    fun move(myTank: View, direction: Direction, elementsOnContainer: List<Element>) {
        val layoutParams = myTank.layoutParams as FrameLayout.LayoutParams
        val currentCoordinate = Coordinate(layoutParams.topMargin, layoutParams.leftMargin) //save before change
        currentDirection = direction
        myTank.rotation = direction.rotation
        when (direction) {
            Direction.UP -> {
                (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += -CELL_SIZE
            }
            Direction.BOTTOM -> {
                (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += CELL_SIZE
            }
            Direction.RIGHT -> {
                (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin += CELL_SIZE
            }
            Direction.LEFT -> {
                (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin += -CELL_SIZE
            }
        }
        val nextCoordinate = Coordinate(layoutParams.topMargin, layoutParams.leftMargin) //save after change
        if (myTank.checkViewCanMoveThroughBorder(nextCoordinate)
            && checkTankCanMoveThroughMaterial(nextCoordinate, elementsOnContainer)
        ) {
            container.removeView(myTank)
            container.addView(myTank, 0)
        } else {
            (myTank.layoutParams as FrameLayout.LayoutParams).topMargin = currentCoordinate.top
            (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin = currentCoordinate.left
        }
    }

    private fun checkTankCanMoveThroughMaterial(coordinate: Coordinate, elementsOnContainer: List<Element>): Boolean {
        getTankCoordinates(coordinate).forEach {
            val element = getElementByCoordinates(it, elementsOnContainer)
            if (element != null && !element.material.tankCanGoThrough) {
                return false
            }
        }
        return true
    }

    private fun getElementByCoordinates(coordinate: Coordinate, elementsOnContainer: List<Element>) =
            elementsOnContainer.firstOrNull { it.coordinate == coordinate }

    private fun checkTankCanMoveThroughBorder(coordinate: Coordinate, myTank: View): Boolean {
        if (coordinate.top >= 0
            && coordinate.top + myTank.height <= HORIZONTAL_MAX_SIZE
            && coordinate.left >= 0
            && coordinate.left + myTank.width <= VERTICAL_MAX_SIZE
        ) {
            return true
        }
        return false
    }

    private fun getTankCoordinates(topLeftCoordinate: Coordinate): List<Coordinate> {
        val coordinateList = mutableListOf<Coordinate>()
        coordinateList.add(topLeftCoordinate)
        coordinateList.add(Coordinate(topLeftCoordinate.top + CELL_SIZE, topLeftCoordinate.left)) //bottom_left
        coordinateList.add(Coordinate(topLeftCoordinate.top, topLeftCoordinate.left + CELL_SIZE)) //top_right
        coordinateList.add(
                Coordinate(
                        topLeftCoordinate.top + CELL_SIZE,
                        topLeftCoordinate.left + CELL_SIZE
                )
        ) //bottom_right
        return coordinateList
    }
}
