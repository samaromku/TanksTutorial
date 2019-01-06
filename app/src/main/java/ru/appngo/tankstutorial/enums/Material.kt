package ru.appngo.tankstutorial.enums

import ru.appngo.tankstutorial.R

const val CELLS_SIMPLE_ELEMENT = 1
const val CELLS_EAGLE_WIDTH = 4
const val CELLS_EAGLE_HEIGHT = 3

enum class Material(
        val tankCanGoThrough: Boolean,
        val bulletCanGoThrough: Boolean,
        val simpleBulletCanDestroy: Boolean,
        val canExistOnlyOne: Boolean,
        val width: Int,
        val height: Int,
        val image: Int?
) {
    EMPTY(
            tankCanGoThrough = true,
            bulletCanGoThrough = true,
            simpleBulletCanDestroy = true,
            canExistOnlyOne = false,
            width = 0,
            height = 0,
            image = null
    ),
    BRICK(
            tankCanGoThrough = false,
            bulletCanGoThrough = false,
            simpleBulletCanDestroy = true,
            canExistOnlyOne = false,
            width = CELLS_SIMPLE_ELEMENT,
            height = CELLS_SIMPLE_ELEMENT,
            image = R.drawable.brick
    ),
    CONCRETE(
            tankCanGoThrough = false,
            bulletCanGoThrough = false,
            simpleBulletCanDestroy = false,
            canExistOnlyOne = false,
            width = CELLS_SIMPLE_ELEMENT,
            height = CELLS_SIMPLE_ELEMENT,
            image = R.drawable.concrete
    ),
    GRASS(
            tankCanGoThrough = true,
            bulletCanGoThrough = true,
            simpleBulletCanDestroy = false,
            canExistOnlyOne = false,
            width = CELLS_SIMPLE_ELEMENT,
            height = CELLS_SIMPLE_ELEMENT,
            image = R.drawable.grass
    ),
    EAGLE(
            tankCanGoThrough = false,
            bulletCanGoThrough = false,
            simpleBulletCanDestroy = true,
            canExistOnlyOne = true,
            width = CELLS_EAGLE_WIDTH,
            height = CELLS_EAGLE_HEIGHT,
            image = R.drawable.eagle
    ),
}
