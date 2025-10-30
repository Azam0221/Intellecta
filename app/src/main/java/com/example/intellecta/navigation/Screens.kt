package com.example.intellecta.navigation

sealed class Screens(val route: String ) {

    data object HomeScreen       : Screens("home_screen")
    data object AddNoteScreen    : Screens("add_note_screen")
    data object NotesListScreen  : Screens("notes_list_screen")
    data object EditNoteScreen   : Screens("edit_note_screen/{noteId}"){
        fun createRoute(noteId: Int) = "edit_note_screen/$noteId"
    }
    data object ChatBot          : Screens("bot_page")
    data object NoteDetailsScreen: Screens("note_details_screen/{noteId}"){
        fun createRoute(noteId: Int) = "note_details_screen/$noteId"
    }
    data object AllFilesScreen   : Screens("all_files")
    data object HomePage   : Screens("home_page")
    data object SearchPage   : Screens("search_page")
    data object ProfilePage   : Screens("profile_page")
    data object BottomNavScreen      : Screens("bottom_nav_screen")
    data object AddNotePage     : Screens("add_note_page")
    data object EditNotePage     : Screens("edit_note_page/{noteId}") {
        fun createRoute(noteId: Int) = "edit_note_page/$noteId"
    }
    data object FileStoragePage     : Screens("file_storage_page")
    data object NoteDetailPage     : Screens("note_detail_page/{noteId}"){
        fun createRoute(noteId: Int) = "note_detail_page/$noteId"
    }
    data object LoginPage     : Screens("login_page")
    data object SignupPage     : Screens("signup_page")
}