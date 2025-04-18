package com.example.mybookeye

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.mybookeye.Controller.NavController
import com.example.mybookeye.Controller.ThemeManager
import com.example.mybookeye.Nav.Navigation
import com.example.mybookeye.ui.theme.MyBookEyeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        ThemeManager.isDarkTheme = prefs.getBoolean("darkMode", false)
        setContent {
            MyBookEyeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navHostController = rememberNavController()
                    val navController = remember { NavController(navHostController) }
                    Navigation(navController)
                }
            }
        }
    }
}