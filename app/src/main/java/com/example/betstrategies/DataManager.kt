package com.example.betstrategies

import android.content.Context

class DataManager(context: Context) {

    private val sharedPref = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun updatePrefsData(id: String, isFavorite: Boolean) {
        val editor = sharedPref.edit()

            editor.putBoolean(id, isFavorite)

        editor.apply()
    }

    fun getPrefsData(keys: List<String>): ArrayList<Boolean> {
        val result = arrayListOf<Boolean>()
        keys.forEach {
            result.add(sharedPref.getBoolean(it, false))
        }
        return result
    }

}