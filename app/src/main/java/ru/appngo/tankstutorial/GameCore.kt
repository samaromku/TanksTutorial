package ru.appngo.tankstutorial

import android.app.Activity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import ru.appngo.tankstutorial.activities.SCORE_REQUEST_CODE
import ru.appngo.tankstutorial.activities.ScoreActivity

class GameCore(private val activity: Activity) {
    @Volatile
    private var isPlay = false
    private var isPlayerOrBaseDestroyed = false
    private var isPlayerWin = false

    fun startOrPauseTheGame() {
        isPlay = !isPlay
    }

    fun isPlaying() = isPlay && !isPlayerOrBaseDestroyed && !isPlayerWin

    fun pauseTheGame() {
        isPlay = false
    }

    fun resumeTheGame(){
        isPlay = true
    }

    fun playerWon(score: Int) {
        isPlayerWin = true
        activity.startActivityForResult(ScoreActivity.createIntent(activity, score), SCORE_REQUEST_CODE)
    }

    fun destroyPlayerOrBase(score: Int) {
        isPlayerOrBaseDestroyed = true
        pauseTheGame()
        animateEndGame(score)
    }

    private fun animateEndGame(score: Int) {
        activity.runOnUiThread {
            val endGameText = activity.findViewById<TextView>(R.id.game_over_text)
            endGameText.visibility = View.VISIBLE
            val slideUp = AnimationUtils.loadAnimation(activity, R.anim.slide_up)
            endGameText.startAnimation(slideUp)
            slideUp.setAnimationListener(object :Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    activity.startActivityForResult(ScoreActivity.createIntent(activity, score), SCORE_REQUEST_CODE)
                }
            })
        }
    }
}
