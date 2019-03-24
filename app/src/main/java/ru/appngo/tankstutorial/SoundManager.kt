package ru.appngo.tankstutorial

import android.content.Context
import android.media.MediaPlayer

class SoundManager(context: Context) {

    private val bulletBurstPlayer = MediaPlayer.create(context, R.raw.bullet_burst)
    private val bulletShotPlayer = MediaPlayer.create(context, R.raw.bullet_shot)
    private val introMusicPlayer = MediaPlayer.create(context, R.raw.tanks_pre_music)
    private val tankMovePlayerFirst = MediaPlayer.create(context, R.raw.tank_move_long)
    private val tankMovePlayerSecond = MediaPlayer.create(context, R.raw.tank_move_long)
    private var isIntroFinished = false

    init {
        prepareGapLessTankMoveSound()
    }

    private fun prepareGapLessTankMoveSound() {
        tankMovePlayerFirst.isLooping = true
        tankMovePlayerSecond.isLooping = true
        tankMovePlayerFirst.setNextMediaPlayer(tankMovePlayerSecond)
        tankMovePlayerSecond.setNextMediaPlayer(tankMovePlayerFirst)
    }

    fun playIntroMusic() {
        if (isIntroFinished) {
            return
        }
        introMusicPlayer.setOnCompletionListener {
            isIntroFinished = true
        }
        introMusicPlayer.start()
    }

    fun pauseSounds() {
        bulletBurstPlayer.pause()
        bulletShotPlayer.pause()
        introMusicPlayer.pause()
        tankMovePlayerFirst.pause()
        tankMovePlayerSecond.pause()
    }

    fun bulletShot() {
        bulletShotPlayer.start()
    }

    fun bulletBurst() {
        bulletBurstPlayer.start()
    }

    fun tankMove() {
        tankMovePlayerFirst.start()
    }

    fun tankStop() {
        if (tankMovePlayerFirst.isPlaying) {
            tankMovePlayerFirst.pause()
        }
        if (tankMovePlayerSecond.isPlaying) {
            tankMovePlayerSecond.pause()
        }
    }
}
