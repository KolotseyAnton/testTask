package by.home.testtaskmovie.utils

import by.home.testtaskmovie.network.models.GetMovieResponse
import by.home.testtaskmovie.network.models.Results
import by.home.testtaskmovie.popularMovie.model.PopularMovieItem
import by.home.testtaskmovie.popularMovieDetails.model.MovieDetail

fun movieDetailsToPopularMovieItem(r:by.home.testtaskmovie.dataManager.entities.MovieDetails,isFavorite:Boolean,baseUrl:String= BASE_URL_FOR_PICTURE)=PopularMovieItem(r.movie.id,r.movie.original_title,r.movie.popularity,r.movie.release_date,baseUrl+r.movie.poster_path,isFavorite)

fun movieDetailsToMovieDetail(r:by.home.testtaskmovie.dataManager.entities.MovieDetails,isFavorite:Boolean,baseUrl:String= BASE_URL_FOR_PICTURE)=MovieDetail(r.movie.original_title,baseUrl+r.movie.backdrop_path,baseUrl+r.movie.poster_path,r.movie.popularity.toString(),r.movie.overview,isFavorite)

//fun getMovieResponseToPopularMovieItem(r:GetMovieResponse,isFavorite:Boolean,baseUrl:String= BASE_URL_FOR_PICTURE)=PopularMovieItem(r.id,r.original_title,r.popularity,r.release_date,baseUrl+r.poster_path,isFavorite)

fun getMovieResponseToMovieDetail(r: GetMovieResponse,isFavorite:Boolean,baseUrl:String= BASE_URL_FOR_PICTURE)=MovieDetail(r.original_title,baseUrl+r.backdrop_path,baseUrl+r.poster_path,r.popularity.toString(),r.overview,isFavorite)

fun resultToPopularMovieItem(r:Results,isFavorite:Boolean,baseUrl:String= BASE_URL_FOR_PICTURE):PopularMovieItem=PopularMovieItem(r.id,r.original_title,r.popularity,r.release_date,baseUrl+r.poster_path,isFavorite)