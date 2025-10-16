package com.example.intellecta.navigation

sealed class Screens(val route: String ) {

    data object HomeScreen       : Screens("home_screen")
    data object AddNoteScreen    : Screens("add_note_screen")
    data object NotesListScreen  : Screens("notes_list_screen")
    data object EditNoteScreen   : Screens("edit_note_screen/{noteId}"){
        fun createRoute(noteId: Int) = "edit_note_screen/$noteId"
    }
    data object ChatBot          : Screens("chat_bot")
    data object NoteDetailsScreen: Screens("note_details_screen/{noteId}"){
        fun createRoute(noteId: Int) = "note_details_screen/$noteId"
    }
    data object AllFilesScreen   : Screens("all_files")
}