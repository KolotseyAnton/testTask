package by.home.testtaskmovie.utils

import by.home.testtaskmovie.dataManager.entities.*
import by.home.testtaskmovie.network.models.GetMovieResponse
import by.home.testtaskmovie.network.models.Results

import by.home.testtaskmovie.popularMovie.model.PopularMovieItem
import by.home.testtaskmovie.popularMovieDetails.model.MovieDetail

fun movieDetailsToPopularMovieItem(r:MovieDetails,isFavorite:Boolean,baseUrl:String= BASE_URL_FOR_PICTURE)=PopularMovieItem(r.movie.id,r.movie.original_title,r.movie.popularity,r.movie.release_date,baseUrl+r.movie.poster_path,isFavorite)

fun movieDetailsToMovieDetail(r:MovieDetails,isFavorite:Boolean,baseUrl:String= BASE_URL_FOR_PICTURE)=MovieDetail(r.movie.original_title,baseUrl+r.movie.backdrop_path,baseUrl+r.movie.poster_path,r.movie.popularity.toString(),r.movie.overview,isFavorite)

//fun getMovieResponseToPopularMovieItem(r:GetMovieResponse,isFavorite:Boolean,baseUrl:String= BASE_URL_FOR_PICTURE)=PopularMovieItem(r.id,r.original_title,r.popularity,r.release_date,baseUrl+r.poster_path,isFavorite)

fun getMovieResponseToMovieDetail(r: GetMovieResponse,isFavorite:Boolean,baseUrl:String= BASE_URL_FOR_PICTURE)=MovieDetail(r.original_title,baseUrl+r.backdrop_path,baseUrl+r.poster_path,r.popularity.toString(),r.overview,isFavorite)

fun getMovieResponseToMovieDetails(movie:GetMovieResponse)= MovieDetails(
    Movie(movie.id,movie.adult,movie.backdrop_path," ",movie.budget,movie.homepage,movie.imdb_id,movie.original_language,movie.original_title,movie.overview,movie.popularity,movie.poster_path,movie.release_date,movie.revenue.toInt(),movie.runtime,movie.status,movie.tagline,movie.original_title,movie.video,movie.vote_average,movie.vote_count),
    movie.production_companies.map { ProductCompany(0,movie.id,it.name) },
    movie.production_countries.map { ProductCountry(0,movie.id,it.name)},
    movie.spoken_languages.map { SpokenLanguage(0,movie.id,it.name) })

fun resultToPopularMovieItem(r:Results,isFavorite:Boolean,baseUrl:String= BASE_URL_FOR_PICTURE):PopularMovieItem=PopularMovieItem(r.id,r.original_title,r.popularity,r.release_date,baseUrl+r.poster_path,isFavorite)