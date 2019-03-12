package ru.appngo.tankstutorial.models

import android.view.View
import ru.appngo.tankstutorial.enums.Direction

data class Bullet(
    val view: View,
    val direction: Direction,
    val tank: Tank,
    var canMoveFurther: Boolean = true
)
