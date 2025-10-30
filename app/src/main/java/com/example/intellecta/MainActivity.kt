package com.example.intellecta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.intellecta.theme.IntellectaTheme
import com.example.intellecta.navigation.Navigation
import com.example.intellecta.ui.screens.LoginPage
import com.example.intellecta.ui.screens.SignupPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntellectaTheme {
               Navigation()


            }
        }
    }
}

