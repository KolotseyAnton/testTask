package by.home.testtaskmovie.network

import by.home.testtaskmovie.network.models.PopularMovieResponse
import by.home.testtaskmovie.network.models.GetMovieResponse
import kotlinx.coroutines.flow.Flow

interface NetworkManager {
    fun getPopularMovies(page:Int): Flow<PopularMovieResponse>
    fun getMovieDetails(movieId:Long): Flow<GetMovieResponse>
    fun search(query:String):Flow<PopularMovieResponse>
    fun getBaseUrl():String
}