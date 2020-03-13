package by.home.testtaskmovie.network

import by.home.testtaskmovie.network.models.PopularMovieResponse
import by.home.testtaskmovie.network.models.GetMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBAPI {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey:String, @Query("page") page:Int, @Query("language") language:String="en-US"):PopularMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id" )movieId:Long, @Query("api_key") apiKey:String): GetMovieResponse

    @GET("search/movie")
    suspend fun search(@Query("api_key") apiKey:String, @Query("query") query:String, @Query("language") language:String="en-US"):PopularMovieResponse
}