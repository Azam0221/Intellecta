package com.example.intellecta.navigation

import androidx.compose.animation.core.tween
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
import com.example.intellecta.chatBot.ChatPage
import com.example.intellecta.ui.screens.AddNoteScreen
import com.example.intellecta.ui.screens.EditNoteScreen
import com.example.intellecta.ui.screens.HomeScreen
import com.example.intellecta.ui.screens.NoteDetailsScreen
import com.example.intellecta.ui.screens.NoteListScreen

@Composable
fun Navigation(){
    val navCtrl = rememberNavController()
    val animationDuration = 300

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
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) + fadeIn(animationSpec = tween(durationMillis = animationDuration))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) + fadeOut(animationSpec = tween(durationMillis = animationDuration))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) + fadeIn(animationSpec = tween(durationMillis = animationDuration))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) + fadeOut(animationSpec = tween(durationMillis = animationDuration))
            }
        ){
            AddNoteScreen(navCtrl)
        }
        composable(Screens.NotesListScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) //+ fadeIn(animationSpec = tween(durationMillis = animationDuration))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) //+ fadeOut(animationSpec = tween(durationMillis = animationDuration))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) //+ fadeIn(animationSpec = tween(durationMillis = animationDuration))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) //+ fadeOut(animationSpec = tween(durationMillis = animationDuration))
            }
        ){
            NoteListScreen(navCtrl)
        }

        composable(Screens.EditNoteScreen.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) + fadeIn(animationSpec = tween(durationMillis = animationDuration))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) + fadeOut(animationSpec = tween(durationMillis = animationDuration))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) + fadeIn(animationSpec = tween(durationMillis = animationDuration))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) + fadeOut(animationSpec = tween(durationMillis = animationDuration))
            }
        ){
                backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            EditNoteScreen(noteId,navCtrl)
        }

        composable(Screens.NoteDetailsScreen.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) + fadeIn(animationSpec = tween(durationMillis = animationDuration))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) + fadeOut(animationSpec = tween(durationMillis = animationDuration))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) + fadeIn(animationSpec = tween(durationMillis = animationDuration))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = animationDuration)
                ) + fadeOut(animationSpec = tween(durationMillis = animationDuration))
            }
        ){
                backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            NoteDetailsScreen(noteId,navCtrl)
        }

        composable(Screens.ChatBot.route){
            ChatPage()
        }
    }
}