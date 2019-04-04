package ru.appngo.tankstutorial.sounds

import android.content.Context
import ru.appngo.tankstutorial.ProgressIndicator
import ru.appngo.tankstutorial.R

private const val INTRO_MUSIC_INDEX = 0
private const val BULLET_SHOT_INDEX = 1
private const val BULLET_BURST_INDEX = 2
private const val TANK_MOVE_INDEX = 3
private const val SUCCESS_UPLOAD = 0

class MainSoundPlayer(val context: Context, val progressIndicator: ProgressIndicator) {

    private val sounds = mutableListOf<GameSound>()
    private val soundPool = SoundPoolFactory().createSoundPool()
    private var soundsReady = 0
    private var allSoundsLoaded = false

    fun loadSounds() {
        progressIndicator.showProgress()
        sounds.add(
                INTRO_MUSIC_INDEX, GameSound(
                resourceInPool = soundPool.load(context, R.raw.tanks_pre_music, 1),
                pool = soundPool
        )
        )
        sounds.add(
                BULLET_SHOT_INDEX, GameSound(
                resourceInPool = soundPool.load(context, R.raw.bullet_shot, 1),
                pool = soundPool
        )
        )
        sounds.add(
                BULLET_BURST_INDEX, GameSound(
                resourceInPool = soundPool.load(context, R.raw.bullet_burst, 1),
                pool = soundPool
        )
        )
        sounds.add(
                TANK_MOVE_INDEX, GameSound(
                resourceInPool = soundPool.load(context, R.raw.tank_move_long, 1),
                pool = soundPool
        )
        )
        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            if (sampleId == sounds[INTRO_MUSIC_INDEX].resourceInPool && status == SUCCESS_UPLOAD) {
                playIntroMusic()
            }
            soundsReady++
            if (soundsReady == sounds.size) {
                progressIndicator.dismissProgress()
                allSoundsLoaded = true
            }
        }
    }

    fun areSoundsReady() = allSoundsLoaded

    fun playIntroMusic() {
        sounds[INTRO_MUSIC_INDEX].startOrResume(isLooping = false)
    }

    fun pauseSounds() {
        pauseSound(INTRO_MUSIC_INDEX)
        pauseSound(BULLET_SHOT_INDEX)
        pauseSound(BULLET_BURST_INDEX)
        pauseSound(TANK_MOVE_INDEX)
    }

    private fun pauseSound(index: Int) {
        sounds[index].pause()
    }

    fun bulletShot() {
        sounds[BULLET_SHOT_INDEX].play()
    }

    fun bulletBurst() {
        sounds[BULLET_BURST_INDEX].play()
    }

    fun tankMove() {
        sounds[TANK_MOVE_INDEX].startOrResume(isLooping = true)
    }

    fun tankStop() {
        sounds[TANK_MOVE_INDEX].pause()
    }
}
