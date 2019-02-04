package ru.appngo.tankstutorial.enums

import ru.appngo.tankstutorial.R

const val CELLS_SIMPLE_ELEMENT = 1
const val CELLS_EAGLE_WIDTH = 4
const val CELLS_EAGLE_HEIGHT = 3
const val CELLS_TANKS_SIZE = 2

enum class Material(
    val tankCanGoThrough: Boolean,
    val bulletCanGoThrough: Boolean,
    val simpleBulletCanDestroy: Boolean,
    val elementsAmountOnScreen: Int,
    val width: Int,
    val height: Int,
    val image: Int?
) {
    EMPTY(
        tankCanGoThrough = true,
        bulletCanGoThrough = true,
        simpleBulletCanDestroy = true,
        elementsAmountOnScreen = 0,
        width = 0,
        height = 0,
        image = null
    ),
    BRICK(
        tankCanGoThrough = false,
        bulletCanGoThrough = false,
        simpleBulletCanDestroy = true,
        elementsAmountOnScreen = 0,
        width = CELLS_SIMPLE_ELEMENT,
        height = CELLS_SIMPLE_ELEMENT,
        image = R.drawable.brick
    ),
    CONCRETE(
        tankCanGoThrough = false,
        bulletCanGoThrough = false,
        simpleBulletCanDestroy = false,
        elementsAmountOnScreen = 0,
        width = CELLS_SIMPLE_ELEMENT,
        height = CELLS_SIMPLE_ELEMENT,
        image = R.drawable.concrete
    ),
    GRASS(
        tankCanGoThrough = true,
        bulletCanGoThrough = true,
        simpleBulletCanDestroy = false,
        elementsAmountOnScreen = 0,
        width = CELLS_SIMPLE_ELEMENT,
        height = CELLS_SIMPLE_ELEMENT,
        image = R.drawable.grass
    ),
    EAGLE(
        tankCanGoThrough = false,
        bulletCanGoThrough = false,
        simpleBulletCanDestroy = true,
        elementsAmountOnScreen = 1,
        width = CELLS_EAGLE_WIDTH,
        height = CELLS_EAGLE_HEIGHT,
        image = R.drawable.eagle
    ),
    ENEMY_TANK(
        tankCanGoThrough = false,
        bulletCanGoThrough = false,
        simpleBulletCanDestroy = true,
        elementsAmountOnScreen = 0,
        width = CELLS_TANKS_SIZE,
        height = CELLS_TANKS_SIZE,
        image = R.drawable.enemy_tank
    ),
    PLAYER_TANK(
        tankCanGoThrough = false,
        bulletCanGoThrough = false,
        simpleBulletCanDestroy = true,
        elementsAmountOnScreen = 1,
        width = CELLS_TANKS_SIZE,
        height = CELLS_TANKS_SIZE,
        image = R.drawable.tank
    ),
}
