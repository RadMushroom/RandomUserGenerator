package example.com.randomusergenerator.data

import android.content.Context
import example.com.randomusergenerator.domain.UserSettings

class UserSettingsStorage(context: Context) : UserSettings {

    companion object {
        private const val PREFERENCES_NAME = "UserSettings"
        private const val KEY_IS_GRID = "isGrid"
    }

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun isGrid() = preferences.getBoolean(KEY_IS_GRID, false)

    override fun setGrid(isGrid: Boolean) {
        preferences.edit().putBoolean(KEY_IS_GRID, isGrid).apply()
    }
}