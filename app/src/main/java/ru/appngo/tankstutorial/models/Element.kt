package ru.appngo.tankstutorial.models

import ru.appngo.tankstutorial.enums.Material

data class Element(
        val viewId: Int,
        val material: Material,
        val coordinate: Coordinate,
        val width: Int,
        val height: Int
)
