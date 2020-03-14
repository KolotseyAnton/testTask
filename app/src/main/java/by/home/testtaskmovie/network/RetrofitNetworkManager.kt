package by.home.testtaskmovie.network

import by.home.testtaskmovie.network.models.PopularMovieResponse
import by.home.testtaskmovie.network.models.GetMovieResponse
import by.home.testtaskmovie.utils.BASE_URL_API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkManager(private  val apiKey:String= "b66ffea8276ce576d60df52600822c88",
                             private val api: MovieDBAPI= Retrofit.Builder()
                                 .baseUrl(BASE_URL_API)
                                 .addConverterFactory(GsonConverterFactory.create())
                                 .build()
                                 .create(MovieDBAPI::class.java)) : NetworkManager{

    @ExperimentalCoroutinesApi
    override fun getPopularMovies(page: Int): Flow<PopularMovieResponse> {
       return flow {
           emit(api.getPopularMovies(apiKey,page))
       }.flowOn(Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    override fun getMovieDetails(movieId: Long): Flow<GetMovieResponse> {
        return flow{
            emit(api.getMovie(movieId,apiKey))
        }.flowOn(Dispatchers.IO)
    }

    override fun search(query: String): Flow<PopularMovieResponse> {
        return flow { emit(api.search(apiKey,query)) }.flowOn(Dispatchers.IO)
    }

    override fun getBaseUrl(): String = BASE_URL_API
}