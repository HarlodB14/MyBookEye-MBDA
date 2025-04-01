package com.example.mybookeye

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mybookeye.controller.UserManager
import com.example.mybookeye.ui.screens.auth.LoginScreen
import com.example.mybookeye.ui.screens.auth.RegisterScreen
import com.example.mybookeye.ui.screens.home.HomeScreen
import com.example.mybookeye.ui.theme.MyBookEyeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBookEyeTheme {
                val userManager = UserManager(applicationContext)
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = if (userManager.isLoggedIn()) "home" else "login"
                ) {
                    composable("login") {
                        LoginScreen(userManager, navController)
                    }
                    composable("register") {
                        RegisterScreen(userManager, navController)
                    }
                    composable("home") {
                        HomeScreen(userManager,navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyBookEyeTheme {
        Greeting("Android")
    }
}