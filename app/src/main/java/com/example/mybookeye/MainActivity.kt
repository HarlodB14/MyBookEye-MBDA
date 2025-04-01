package com.example.mybookeye

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mybookeye.controller.UserManager
import com.example.mybookeye.ui.screens.auth.LoginScreen
import com.example.mybookeye.ui.screens.auth.RegisterScreen
import com.example.mybookeye.ui.screens.auth.StartScreen
import com.example.mybookeye.ui.screens.home.HomeScreen
import com.example.mybookeye.ui.theme.MyBookEyeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userManager = UserManager(applicationContext)
        setContent {
            MyBookEyeTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "start"
                ) {
                    composable("start") {
                        StartScreen(navController)
                    }
                    composable("login") {
                        LoginScreen(navController, userManager)
                    }
                    composable("register") {
                        RegisterScreen(navController, userManager)
                    }
                    composable("home") {
                        HomeScreen(navController, userManager)
                    }
                }
            }
        }
    }
}