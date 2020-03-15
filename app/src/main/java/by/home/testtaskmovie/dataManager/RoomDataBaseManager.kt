package by.home.testtaskmovie.dataManager

import android.content.Context
import by.home.testtaskmovie.dataManager.entities.*
import by.home.testtaskmovie.network.models.GetMovieResponse
import by.home.testtaskmovie.utils.getMovieResponseToMovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RoomDataBaseManager(context: Context, private val roomDB: RoomDB= RoomDB.getInstance(context)):DatabaseManager {

    @ExperimentalCoroutinesApi
    override fun findMovieDetailById(id: Long):Flow<MovieDetails?> {
        return flow{ emit(roomDB.movieDetailsDao().getAllMovieDetails().firstOrNull { movieDetails -> movieDetails.movie.id==id  })}.flowOn(Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    override fun saveMovieDetails(movie: GetMovieResponse):Flow<Boolean> {
        val m= getMovieResponseToMovieDetails(movie)

        return flow {
            m.apply {
                roomDB.movieDao().insert(m.movie)
                roomDB.productCompanyDao().insert(listProductCompany)
                roomDB.productCountryDao().insert(listProductCountry)
                roomDB.spokenLanguageDao().insert(listSpokenLanguage)
            }
            emit( true)

        }.flowOn(Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    override fun removeMovieDetails(id: Long): Flow<Boolean> {
        return flow {
            roomDB.movieDao().getMovie(id).firstOrNull()?.let {
                roomDB.movieDao().delete(it)
                emit(true)
            }?: error("item is not in database")
        }.flowOn(Dispatchers.IO)
    }


    @ExperimentalCoroutinesApi
    override fun getAllMovieDetail(): Flow<List<MovieDetails>> {
        return flow { emit(roomDB.movieDetailsDao().getAllMovieDetails()) }.flowOn(Dispatchers.IO)
    }
}