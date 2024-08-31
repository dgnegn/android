package com.example.bottomnavbarapp


sealed class NavHostGraphRoute(val route: String) {
    data object NotesGraph : NavHostGraphRoute("notes_graph")
    data object NotesDetailGraph : NavHostGraphRoute("notes_detail_graph")
    data object NotesDetail : NavHostGraphRoute("notes_detail")

}
