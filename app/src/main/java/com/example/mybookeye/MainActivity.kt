package com.example.mybookeye

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.mybookeye.Controller.NavController
import com.example.mybookeye.Nav.Navigation
import com.example.mybookeye.ui.theme.MyBookEyeTheme

// MainActivity.kt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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