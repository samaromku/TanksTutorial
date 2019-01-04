package ru.appngo.tankstutorial

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.appngo.tankstutorial.models.Element

const val KEY_LEVEL = "key_level"

class LevelStorage(val context: Context) {
    private val prefs = (context as Activity).getPreferences(MODE_PRIVATE)

    fun saveLevel(elementsOnContainer: List<Element>) {
        prefs.edit()
                .putString(KEY_LEVEL, Gson().toJson(elementsOnContainer))
                .apply()
    }

    fun loadLevel(): List<Element>? {
        val levelFromPrefs = prefs.getString(KEY_LEVEL, null)
        levelFromPrefs?.let {
            val type = object : TypeToken<List<Element>>() {}.type
            return Gson().fromJson(it, type)
        }
    }
}
