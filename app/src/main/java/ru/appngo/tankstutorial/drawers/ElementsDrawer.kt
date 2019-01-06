package ru.appngo.tankstutorial.drawers

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import ru.appngo.tankstutorial.CELL_SIZE
import ru.appngo.tankstutorial.enums.Material
import ru.appngo.tankstutorial.models.Coordinate
import ru.appngo.tankstutorial.models.Element
import ru.appngo.tankstutorial.utils.getElementByCoordinates

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
        val elementOnCoordinate = getElementByCoordinates(coordinate, elementsOnContainer)
        if (elementOnCoordinate == null) {
            drawView(coordinate)
            return
        }
        if (elementOnCoordinate.material != currentMaterial) {
            replaceView(coordinate)
        }
    }

    fun drawElementsList(elements: List<Element>?) {
        if (elements == null) {
            return
        }
        for (element in elements) {
            currentMaterial = element.material
            drawView(element.coordinate)
        }
    }

    private fun replaceView(coordinate: Coordinate) {
        eraseView(coordinate)
        drawView(coordinate)
    }

    private fun eraseView(coordinate: Coordinate) {
        removeElement(getElementByCoordinates(coordinate, elementsOnContainer))
        for (erasingElement in getElementsUnderCurrentMaterial(coordinate)) {
            removeElement(erasingElement)
        }
    }

    private fun removeElement(element: Element?) {
        if (element != null) {
            val erasingView = container.findViewById<View>(element.viewId)
            container.removeView(erasingView)
            elementsOnContainer.remove(element)
        }
    }

    private fun getElementsUnderCurrentMaterial(coordinate: Coordinate): List<Element> {
        val elementsList = mutableListOf<Element>()
        for (element in elementsOnContainer) {
            for (height in 0 until currentMaterial.height) {
                for (width in 0 until currentMaterial.width) {
                    if (element.coordinate == Coordinate(
                                coordinate.top + height * CELL_SIZE,
                                coordinate.left + width * CELL_SIZE)) {
                        elementsList.add(element)
                    }
                }
            }
        }
        return elementsList
    }

    private fun drawView(coordinate: Coordinate) {
        removeIfSingleInstance()
        val view = ImageView(container.context)
        val layoutParams = FrameLayout.LayoutParams(
                currentMaterial.width * CELL_SIZE,
                currentMaterial.height * CELL_SIZE)
        currentMaterial.image?.let { view.setImageResource(it) }
        layoutParams.topMargin = coordinate.top
        layoutParams.leftMargin = coordinate.left
        val element = Element(
                material = currentMaterial,
                coordinate = coordinate,
                width = currentMaterial.width,
                height = currentMaterial.height)
        view.id = element.viewId
        view.layoutParams = layoutParams
        view.scaleType = ImageView.ScaleType.FIT_XY
        container.addView(view)
        elementsOnContainer.add(element)
    }

    private fun removeIfSingleInstance() {
        if (currentMaterial.canExistOnlyOne) {
            elementsOnContainer.firstOrNull { it.material == currentMaterial }?.coordinate?.let { eraseView(it) }
        }
    }
}
