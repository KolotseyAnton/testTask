package by.home.testtaskmovie.popularMovie.model

data class PopularMovieItem(val id:Long, val name:String, val rating:Double, val year:String,val imageUrl:String, var isFavorite:Boolean)