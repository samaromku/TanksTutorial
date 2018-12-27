package ru.appngo.tankstutorial.drawers

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import ru.appngo.tankstutorial.CELL_SIZE
import ru.appngo.tankstutorial.R
import ru.appngo.tankstutorial.enums.Direction
import ru.appngo.tankstutorial.models.Coordinate

private const val BULLET_WIDTH = 15
private const val BULLET_HEIGHT = 25

class BulletDrawer(val container: FrameLayout) {
    fun drawBullet(myTank: View, currentDirection: Direction) {
        val bullet = ImageView(container.context)
                .apply {
                    this.setImageResource(R.drawable.bullet)
                    this.layoutParams = FrameLayout.LayoutParams(BULLET_WIDTH, BULLET_HEIGHT)
                    val bulletCoordinate = getBulletCoordinates(this, myTank, currentDirection)
                    (this.layoutParams as FrameLayout.LayoutParams).topMargin = bulletCoordinate.top
                    (this.layoutParams as FrameLayout.LayoutParams).leftMargin = bulletCoordinate.left
                    this.rotation = currentDirection.rotation
                }
        container.addView(bullet)
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
