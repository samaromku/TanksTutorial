package ru.appngo.tankstutorial.drawers

import android.support.annotation.DrawableRes
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import ru.appngo.tankstutorial.CELL_SIZE
import ru.appngo.tankstutorial.R
import ru.appngo.tankstutorial.enums.Material
import ru.appngo.tankstutorial.models.Coordinate
import ru.appngo.tankstutorial.models.Element
import ru.appngo.tankstutorial.utils.getElementByCoordinates

const val CELLS_SIMPLE_ELEMENT = 1
const val CELLS_EAGLE_WIDTH = 4
const val CELLS_EAGLE_HEIGHT = 3

class ElementsDrawer(val container: FrameLayout) {

    var currentMaterial = Material.EMPTY
    val elementsOnContainer = mutableListOf<Element>()

    fun onTouchContainer(x: Float, y: Float) {
        val topMargin = y.toInt() - (y.toInt() % CELL_SIZE)
        val leftMargin = x.toInt() - (x.toInt() % CELL_SIZE)
        val coordinate = Coordinate(topMargin, leftMargin)
        if (currentMaterial == Material.EMPTY) {
            eraseView(coordinate)
        } else {
            drawOrReplaceView(coordinate)
        }
    }

    private fun drawOrReplaceView(coordinate: Coordinate) {
        val viewOnCoordinate = getElementByCoordinates(coordinate, elementsOnContainer)
        if (viewOnCoordinate == null) {
            selectMaterial(coordinate)
            return
        }
        if (viewOnCoordinate.material != currentMaterial) {
            replaceView(coordinate)
        }
    }

    fun drawElementsList(elements: List<Element>?) {
        if (elements == null) {
            return
        }
        for (element in elements) {
            currentMaterial = element.material
            selectMaterial(element.coordinate)
        }
    }

    private fun replaceView(coordinate: Coordinate) {
        eraseView(coordinate)
        selectMaterial(coordinate)
    }

    private fun eraseView(coordinate: Coordinate) {
        val elementOnCoordinate = getElementByCoordinates(coordinate, elementsOnContainer)
        if (elementOnCoordinate != null) {
            val erasingView = container.findViewById<View>(elementOnCoordinate.viewId)
            container.removeView(erasingView)
            elementsOnContainer.remove(elementOnCoordinate)
        }
    }

    private fun selectMaterial(coordinate: Coordinate) {
        when (currentMaterial) {
            Material.BRICK -> drawView(R.drawable.brick, coordinate)
            Material.CONCRETE -> drawView(R.drawable.concrete, coordinate)
            Material.GRASS -> drawView(R.drawable.grass, coordinate)
            Material.EAGLE -> {
                removeExistingEagle()
                drawView(R.drawable.eagle, coordinate, CELLS_EAGLE_WIDTH, CELLS_EAGLE_HEIGHT)
            }
            Material.EMPTY -> {
            }
        }
    }

    private fun removeExistingEagle() {
        elementsOnContainer.firstOrNull { it.material == Material.EAGLE }?.coordinate?.let { eraseView(it) }
    }

    private fun drawView(
            @DrawableRes image: Int,
            coordinate: Coordinate,
            width: Int = CELLS_SIMPLE_ELEMENT,
            height: Int = CELLS_SIMPLE_ELEMENT
    ) {
        val view = ImageView(container.context)
        val layoutParams = FrameLayout.LayoutParams(width * CELL_SIZE, height * CELL_SIZE)
        view.setImageResource(image)
        layoutParams.topMargin = coordinate.top
        layoutParams.leftMargin = coordinate.left
        val viewId = View.generateViewId()
        view.id = viewId
        view.layoutParams = layoutParams
        container.addView(view)
        elementsOnContainer.add(Element(viewId, currentMaterial, coordinate, width, height))
    }
}
