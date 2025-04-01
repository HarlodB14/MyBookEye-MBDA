package com.example.mybookeye.controller

import android.content.Context
import com.example.mybookeye.model.PasswordUtils
import androidx.core.content.edit
import com.example.mybookeye.model.User
class UserManager(private val context: Context) {
    private val sharedPrefs = context.getSharedPreferences(
        "auth_prefs",
        Context.MODE_PRIVATE
    )

    fun register(user: User): Boolean {
        if (sharedPrefs.contains(user.email)) return false

        sharedPrefs.edit {
            putString("email", user.email)
            putString("username", user.username)
            putString("passwordHash", user.passwordHash)
        }
        return true
    }

    fun login(email: String, password: String): User? {
        val storedEmail = sharedPrefs.getString("email", null)
        val storedUsername = sharedPrefs.getString("username", null)
        val storedPasswordHash = sharedPrefs.getString("passwordHash", null)

        return if (storedEmail == email &&
            PasswordUtils.verify(password, storedPasswordHash.orEmpty())) {
            User(
                username = storedUsername ?: "",
                email = storedEmail,
                passwordHash = storedPasswordHash ?: ""
            )
        } else {
            null
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPrefs.contains("email")
    }

    fun getCurrentUser(): User? {
        return if (isLoggedIn()) {
            User(
                username = sharedPrefs.getString("username", "") ?: "",
                email = sharedPrefs.getString("email", "") ?: "",
                passwordHash = sharedPrefs.getString("passwordHash", "") ?: ""
            )
        } else {
            null
        }
    }
}