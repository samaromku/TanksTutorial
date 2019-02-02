package ru.appngo.tankstutorial.models

import android.view.View
import ru.appngo.tankstutorial.enums.Material

data class Element (
        val viewId: Int = View.generateViewId(),
        val material: Material,
        var coordinate: Coordinate,
        val width: Int,
        val height: Int
)
