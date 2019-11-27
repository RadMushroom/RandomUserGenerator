package example.com.randomusergenerator

import android.app.Application
import example.com.randomusergenerator.data.UserSettingsStorage
import example.com.randomusergenerator.domain.UserSettings

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
            private set
    }

    lateinit var userSettings: UserSettings
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        userSettings = UserSettingsStorage(this)
    }
}