package by.home.testtaskmovie.popularMovieDetails.interactor

import by.home.testtaskmovie.dataManager.DatabaseManager
import by.home.testtaskmovie.network.NetworkManager
import by.home.testtaskmovie.popularMovieDetails.model.MovieDetail
import by.home.testtaskmovie.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class MovieDetailsInteractor(private val networkManager: NetworkManager,private val dbManager:DatabaseManager) {
    @ExperimentalCoroutinesApi
    fun getMovieDetails(id:Long): Flow<MovieDetail>{
       return networkManager.getMovieDetails(id)
           .zip(dbManager.isFavoriteMovie(id),transform = {m,isFav->getMovieResponseToMovieDetail(m,isFav)})
           .catch {
               emit(dbManager.findMovieDetailById(id)
                   .map {
                       it?.let {  movieDetailsToMovieDetail(it,true)}
                           ?: error("Error: movie is not favorite")
                   }.first())
           }

    }
    fun addToFavorites(id: Long):Flow<Boolean>{
        return dbManager.addToFavorites(networkManager,id)
    }
    
    fun removeFromFavorites(id: Long):Flow<Boolean>{
        return dbManager.removeMovieDetails(id)
    }
}
