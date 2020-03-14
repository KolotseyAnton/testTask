package by.home.testtaskmovie.dataManager.dao

import androidx.room.*
import by.home.testtaskmovie.dataManager.entities.*

@Dao
interface MovieDetailsDao{
    @Transaction
    @Query("SELECT * from movie")
    fun getAllMovieDetails(): List<MovieDetails>
}
@Dao
interface MovieDao{

    @Query("SELECT * from movie")
    fun getAllMovies():List<Movie>


    @Query("SELECT * from movie WHERE id = :movie_id")
    fun getMovie(movie_id:Long):List<Movie>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie:Movie)

    @Delete
    fun delete(movie: Movie)
}
@Dao
interface GenreDao{
    @Query("SELECT * from genre")
    fun getAllGenries():List<Genre>
    @Query("SELECT * from genre WHERE id = :genre_id")
    fun getMovie(genre_id:Long):List<Genre>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(genre: Genre)
    @Delete
    fun delete(genre:Genre)
}
@Dao
interface ProductCountryDao{
    @Query("SELECT * from product_country")
    fun getAllCountries():List<ProductCountry>
    @Query("SELECT * from product_country WHERE product_country_id = :country_id")
    fun getMovie(country_id:Long):List<ProductCountry>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(productCountry: ProductCountry)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(productCountry: List<ProductCountry>)
    @Query("DELETE from product_country WHERE product_country_id ==:id")
    fun delete(id:Long)
}

@Dao
interface ProductCompanyDao{

    @Query("SELECT * from product_company")
    fun getAllCountries():List<ProductCompany>
    @Query("SELECT * from product_company WHERE product_company_id = :company_id")
    fun getMovie(company_id:Long):List<ProductCompany>

    @Insert
    fun insert(productCompany: ProductCompany)

    @Insert
    fun insert(productCompany: List<ProductCompany>)
    @Delete
    fun delete(productCompany:ProductCompany)

}

@Dao
interface SpokenLanguageDao{
    @Query("SELECT * from spoken_language")
    fun getAllCountries():List<SpokenLanguage>

    @Query("SELECT * from spoken_language WHERE spoken_language_id = :id")
    fun getMovie(id:Long):List<SpokenLanguage>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(spokenLanguage: SpokenLanguage)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(spokenLanguage: List<SpokenLanguage>)

    @Delete
    fun delete(spokenLanguage:SpokenLanguage)
}
