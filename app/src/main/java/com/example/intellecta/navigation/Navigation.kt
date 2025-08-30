package com.example.intellecta.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.intellecta.ui.screens.AddNoteScreen
import com.example.intellecta.ui.screens.EditNoteScreen
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
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
        ){
            AddNoteScreen(navCtrl)
        }
        composable(Screens.NotesListScreen.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1 }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { 1 }) + fadeOut()
            },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { 1}) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1 }) + fadeOut() }
        ){
            NoteListScreen(navCtrl)
        }

        composable(Screens.EditNoteScreen.route,
            arguments = listOf(navArgument("noteId"){type = NavType.IntType}),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
            },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }){
                backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            EditNoteScreen(noteId,navCtrl)
        }
    }
}