package com.example.intellecta.navigation

sealed class Screens(val route: String ) {

    data object HomeScreen      : Screens("home_screen")
    data object AddNoteScreen   : Screens("add_note_screen")
}