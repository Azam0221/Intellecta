package com.example.intellecta.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intellecta.ui.screens.AddNoteScreen
import com.example.intellecta.ui.screens.HomeScreen

@Composable
fun Navigation(){
    val navCtrl = rememberNavController()

    NavHost(
        navController = navCtrl,
        startDestination = Screens.HomeScreen.route
    ){
        composable(Screens.HomeScreen.route){
            HomeScreen(navCtrl)
        }
        composable(Screens.AddNoteScreen.route){
            AddNoteScreen()
        }
    }
}