package com.example.intellecta.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intellecta.ui.screens.AddNoteScreen
import com.example.intellecta.ui.screens.HomeScreen
import com.example.intellecta.ui.screens.NoteListScreen

@Composable
fun Navigation(){
    val navCtrl = rememberNavController()

    NavHost(
        navController = navCtrl,
        startDestination = Screens.HomeScreen.route
    ){
        composable(Screens.HomeScreen.route
           ){
            HomeScreen(navCtrl)
        }
        composable(Screens.AddNoteScreen.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
            },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }){
            AddNoteScreen()
        }
        composable(Screens.NotesListScreen.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
            },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }){
            NoteListScreen()
        }
    }
}