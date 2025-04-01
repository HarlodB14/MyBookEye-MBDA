package com.example.mybookeye.controller

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns
import androidx.core.content.edit
import com.example.mybookeye.model.PasswordUtils
import com.example.mybookeye.model.User

class UserManager(context: Context) {
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(
        "auth_prefs",
        Context.MODE_PRIVATE
    )


    fun register(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        try {
            if (user.email.isBlank() || user.passwordHash.isBlank()) {
                throw IllegalArgumentException("Email and password cannot be empty")
            }

            sharedPrefs.edit {
                putString("user_id", user.id.toString())
                putString("username", user.username)
                putString("email", user.email)
                putString("passwordHash", user.passwordHash)
                putBoolean("is_logged_in", true)
            }

            onSuccess()
        } catch (e: Exception) {
            onError(e.message ?: "Registration failed")
        }
    }

    fun login(email: String, password: String): User? {
        if (!doesEmailExist(email)) return null

        val storedPasswordHash = sharedPrefs.getString("passwordHash", null) ?: return null
        return if (PasswordUtils.verify(password, storedPasswordHash)) {
            User(
                username = sharedPrefs.getString("username", "") ?: "",
                email = email,
                passwordHash = storedPasswordHash
            ).also {
                sharedPrefs.edit() { putBoolean("is_logged_in", true) }
            }
        } else {
            null
        }
    }

    fun isValidEmail(email: String): Boolean {
        if (email.isBlank()) return false
        val pattern = Patterns.EMAIL_ADDRESS
        if (!pattern.matcher(email).matches()) return false

        return email.run {
            count { it == '@' } == 1 &&
                    substringAfter('@').contains('.') &&
                    !contains(" ")
        }
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

    fun logout() {
        sharedPrefs.edit() { putBoolean("is_logged_in", false) }
    }

    fun isLoggedIn(): Boolean {
        return sharedPrefs.getBoolean("is_logged_in", false) &&
                !sharedPrefs.getString("email", "").isNullOrEmpty() &&
                !sharedPrefs.getString("passwordHash", "").isNullOrEmpty()
    }

    fun doesEmailExist(email: String): Boolean {
        return sharedPrefs.getString("email", null)?.equals(email, ignoreCase = true) == true
    }

}
