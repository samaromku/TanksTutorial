package ru.appngo.tankstutorial.sounds

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build

class SoundPoolFactory {
    private val maxStreamsAmount = 6
    fun createSoundPool(): SoundPool {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder()
                .setMaxStreams(maxStreamsAmount)
                .build()
        } else {
            SoundPool(maxStreamsAmount, AudioManager.STREAM_MUSIC, 0)
        }
    }
}
