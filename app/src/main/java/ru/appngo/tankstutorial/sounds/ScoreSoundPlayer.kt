package ru.appngo.tankstutorial.sounds

import android.content.Context
import ru.appngo.tankstutorial.R

class ScoreSoundPlayer(
    private val context: Context,
    private val soundReadyListener: () -> Unit
) {
    private lateinit var scoreSound: GameSound
    private val soundPool = SoundPoolFactory().createSoundPool()

    init {
        loadSounds()
    }

    private fun loadSounds() {
        scoreSound = GameSound(
                resourceInPool = soundPool.load(context, R.raw.score_count, 1),
                pool = soundPool
        )
    }

    fun playScoreSound() {
        soundPool.setOnLoadCompleteListener { _, _, _ ->
            soundReadyListener()
            scoreSound.startOrResume(isLooping = true)
        }
    }

    fun pauseScoreSound() {
        scoreSound.pause()
    }
}
