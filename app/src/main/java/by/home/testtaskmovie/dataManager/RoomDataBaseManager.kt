package by.home.testtaskmovie.dataManager

import android.content.Context
import by.home.testtaskmovie.dataManager.entities.*
import by.home.testtaskmovie.network.models.GetMovieResponse
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

        val m= MovieDetails(Movie(movie.id,movie.adult,movie.backdrop_path," ",movie.budget,movie.homepage,movie.imdb_id,movie.original_language,movie.original_title,movie.overview,movie.popularity,movie.poster_path,movie.release_date,movie.revenue.toInt(),movie.runtime,movie.status,movie.tagline,movie.original_title,movie.video,movie.vote_average,movie.vote_count),
            movie.production_companies.map { ProductCompany(0,movie.id,it.name) },
            movie.production_countries.map { ProductCountry(0,movie.id,it.name)},
            movie.spoken_languages.map { SpokenLanguage(0,movie.id,it.name) })

        return flow {

            roomDB.movieDao().insert(m.movie)
            m.apply {
                listProductCompany.forEach {
                    roomDB.productCompanyDao().insert(it)
                }
                listProductCountry.forEach {
                    roomDB.productCountryDao().insert(it)
                }
                listSpokenLanguage.forEach {
                    roomDB.spokenLanguageDao().insert(it)
                }
            }
            emit( true) }.flowOn(Dispatchers.IO)
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