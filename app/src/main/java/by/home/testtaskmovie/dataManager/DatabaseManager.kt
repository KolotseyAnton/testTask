package by.home.testtaskmovie.dataManager

import by.home.testtaskmovie.dataManager.entities.MovieDetails
import by.home.testtaskmovie.network.models.GetMovieResponse
import kotlinx.coroutines.flow.Flow

interface DatabaseManager {
    fun getAllMovieDetail():Flow<List<MovieDetails>>
    fun findMovieDetailById(id:Long):Flow<MovieDetails?>
    fun saveMovieDetails(movie:GetMovieResponse):Flow<Boolean>
    fun removeMovieDetails(id:Long):Flow<Boolean>
}

