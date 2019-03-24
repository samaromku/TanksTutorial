package ru.appngo.tankstutorial

import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView

class GameCore(private val activity: Activity) {
    @Volatile
    private var isPlay = false
    private var isPlayerOrBaseDestroyed = false

    fun startOrPauseTheGame() {
        isPlay = !isPlay
    }

    fun isPlaying() = isPlay && !isPlayerOrBaseDestroyed

    fun pauseTheGame() {
        isPlay = false
    }

    fun destroyPlayerOrBase() {
        isPlayerOrBaseDestroyed = true
        pauseTheGame()
        animateEndGame()
    }

    private fun animateEndGame() {
        activity.runOnUiThread {
            val endGameText = activity.findViewById<TextView>(R.id.game_over_text)
            endGameText.visibility = View.VISIBLE
            val slideUp = AnimationUtils.loadAnimation(activity, R.anim.slide_up)
            endGameText.startAnimation(slideUp)
        }
    }
}
