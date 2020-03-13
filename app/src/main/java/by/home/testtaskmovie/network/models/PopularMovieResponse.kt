package by.home.testtaskmovie.network.models

data class PopularMovieResponse(
    val page : Int,
    val total_results : Int,
    val total_pages : Int,
    val results : List<Results>)