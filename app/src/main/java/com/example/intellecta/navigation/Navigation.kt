package com.example.intellecta.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.intellecta.chatBot.ChatPage
import com.example.intellecta.data.TokenManager
import com.example.intellecta.model.AuthState
import com.example.intellecta.ui.screens.AddNotePage
import com.example.intellecta.ui.screens.AddNoteScreen
import com.example.intellecta.ui.screens.AllFilesScreen
import com.example.intellecta.ui.screens.EditNotePage
import com.example.intellecta.ui.screens.EditNoteScreen
import com.example.intellecta.ui.screens.FileStoragePage
import com.example.intellecta.ui.screens.HomePage
import com.example.intellecta.ui.screens.HomeScreen
import com.example.intellecta.ui.screens.LoginPage
import com.example.intellecta.ui.screens.NoteDetailPage
import com.example.intellecta.ui.screens.NoteDetailsScreen
import com.example.intellecta.ui.screens.NoteListScreen
import com.example.intellecta.ui.screens.SignupPage
import com.example.intellecta.ui.screens.TestApiScreen
import com.example.intellecta.viewmodel.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(){
    val navCtrl = rememberNavController()
    val animationDuration = 300
    val authViewModel : AuthViewModel = koinViewModel()
    val authState = authViewModel.authState.collectAsState()

    NavHost(
        navController = navCtrl,
        startDestination = if (authState.value == AuthState.Authenticated) Screens.BottomNavScreen.route else Screens.LoginPage.route
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

        composable(Screens.BottomNavScreen.route){
            BottomNavBar(navCtrl)
        }

        composable(Screens.AllFilesScreen.route){
            AllFilesScreen(navCtrl)
        }
        composable(Screens.AddNotePage.route){
            AddNotePage(navCtrl)
        }
        composable(Screens.EditNotePage.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType }),){
            backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            EditNotePage(noteId,navCtrl)
        }

        composable(Screens.FileStoragePage.route){
           FileStoragePage(navCtrl)
        }

        composable(Screens.ChatBot.route){
            ChatPage(navCtrl)
        }

        composable(Screens.NoteDetailPage.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType }),){
                backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            NoteDetailPage(noteId,navCtrl)
        }

        composable(Screens.LoginPage.route){
            LoginPage(navCtrl)
        }

        composable(Screens.SignupPage.route){
            SignupPage(navCtrl)
        }

        composable(Screens.TestScreen.route){
           TestApiScreen()
        }


    }
}