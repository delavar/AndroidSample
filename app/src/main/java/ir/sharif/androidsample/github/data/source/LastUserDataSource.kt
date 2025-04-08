package ir.sharif.androidsample.github.data.source

import android.content.SharedPreferences
import androidx.core.content.edit

class LastUserDataSource(private val sharedPreferences: SharedPreferences) {
    fun saveLastUsername(username: String) {
        sharedPreferences.edit {
            putString(LAST_USERNAME_KEY, username)
            apply()
        }
    }

    fun getLastUsername(): String? {
        return sharedPreferences.getString(LAST_USERNAME_KEY, null)
    }

    companion object {
        private const val LAST_USERNAME_KEY = "last_username"
    }
}