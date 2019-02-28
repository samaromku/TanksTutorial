package ru.appngo.tankstutorial

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.appngo.tankstutorial.models.Element

const val KEY_LEVEL = "key_level"

class LevelStorage(context: Context) {
    private val prefs = (context as Activity).getPreferences(MODE_PRIVATE)
    private val gson = Gson()

    fun saveLevel(elementsOnContainer: List<Element>) {
        prefs.edit()
            .putString(KEY_LEVEL, gson.toJson(elementsOnContainer))
            .apply()
    }

    fun loadLevel(): List<Element>? {
        val levelFromPrefs = prefs.getString(KEY_LEVEL, null) ?: return null
        val type = object : TypeToken<List<Element>>() {}.type
        val elementsFromStorage: List<Element> = gson.fromJson(levelFromPrefs, type)
        val elementsWithNewIds = mutableListOf<Element>()
        elementsFromStorage.forEach {
            elementsWithNewIds.add(
                Element(
                    material = it.material,
                    coordinate = it.coordinate
                )
            )
        }
        return elementsWithNewIds
    }
}
