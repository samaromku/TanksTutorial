package ru.appngo.tankstutorial.sounds

import android.media.SoundPool

class GameSound(
    var resourceInPool: Int,
    var isStarted: Boolean = false,
    val pool: SoundPool
) {

    fun play() {
        pool.play(resourceInPool, 1f, 1f, 1, 0, 1f)
    }

    fun startOrResume(isLooping: Boolean) {
        if (isStarted) {
            pool.resume(resourceInPool)
        } else {
            isStarted = true
            resourceInPool = pool.play(resourceInPool, 1f, 1f, 1, isLooping.toInt(), 1f)
        }
    }

    private fun Boolean.toInt() =
        if (this) {
            -1
        } else {
            0
        }

    fun pause() {
        pool.pause(resourceInPool)
    }
}
