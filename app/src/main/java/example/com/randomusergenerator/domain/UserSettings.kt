package example.com.randomusergenerator.domain

interface UserSettings {
    fun isGrid(): Boolean
    fun setGrid(isGrid: Boolean)
}