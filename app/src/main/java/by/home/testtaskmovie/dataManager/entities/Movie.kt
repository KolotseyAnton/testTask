package by.home.testtaskmovie.dataManager.entities

import androidx.room.*


@Entity(tableName = "movie")
data class Movie(@PrimaryKey
                 @ColumnInfo(name = "id") val id:Long,
                 @ColumnInfo(name = "adult") val adult: Boolean,
                 @ColumnInfo(name = "backdrop_path") val backdrop_path: String,
                 @ColumnInfo(name = "belongs_to_collection") val belongs_to_collection: String,
                 @ColumnInfo(name = "budget") val budget: Int,
                        //genre
                 @ColumnInfo(name = "homepage") val homepage: String,
                 @ColumnInfo(name = "imdb_id") val imdb_id: String,
                 @ColumnInfo(name = "original_language") val original_language: String,
                 @ColumnInfo(name = "original_title") val original_title: String,
                 @ColumnInfo(name = "overview") val overview: String,
                 @ColumnInfo(name = "popularity") val popularity: Double,
                 @ColumnInfo(name = "poster_path") val poster_path: String,
                        //product Company
                      //  val production_countries: List<ProductionCountry>,
                 @ColumnInfo(name = "release_date") val release_date: String,
                 @ColumnInfo(name = "revenue") val revenue: Int,
                 @ColumnInfo(name = "runtime") val runtime: Int,
                       // val spoken_languages: List<SpokenLanguage>,
                 @ColumnInfo(name = "status") val status: String,
                 @ColumnInfo(name = "tagline") val tagline: String,
                 @ColumnInfo(name = "title") val title: String,
                 @ColumnInfo(name = "video") val video: Boolean,
                 @ColumnInfo(name = "vote_average") val vote_average: Double,
                 @ColumnInfo(name = "vote_count") val vote_count: Int ){
    constructor():this(-1,false,"","",0,"","","","","",0.0,"","",0,0,"","","",false,0.0,0 )
}

@Entity(tableName = "genre")
data class Genre ( @PrimaryKey val id:Long,@ColumnInfo(name = "name") val name:String)

@Entity(tableName = "product_company",
    foreignKeys = [ForeignKey(
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movie_id"),
        entity = Movie::class,
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("movie_id")])
data class ProductCompany(@PrimaryKey(autoGenerate = true) val product_company_id:Long, @ColumnInfo(name = "movie_id") val movie_id:Long,@ColumnInfo(name = "name") val name:String)


@Entity(tableName = "product_country",
    foreignKeys = [ForeignKey(
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movie_id"),
        entity = Movie::class,
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("movie_id")])
data class ProductCountry(@PrimaryKey(autoGenerate = true)  @ColumnInfo(name = "product_country_id") val product_country_id:Long, @ColumnInfo(name = "movie_id") val movie_id:Long,  @ColumnInfo(name = "name") val name:String){

}

@Entity(tableName = "spoken_language",
    foreignKeys = [ForeignKey(
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movie_id"),
        entity = Movie::class,
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("movie_id")])
data class SpokenLanguage(@PrimaryKey(autoGenerate = true) val spoken_language_id:Long, @ColumnInfo(name = "movie_id") val movie_id:Long,  @ColumnInfo(name = "name") val name:String)

class MovieDetails(
    @Embedded var movie: Movie,
    @Relation(parentColumn = "id", entityColumn = "movie_id") var listProductCompany:List<ProductCompany>,
    @Relation(parentColumn = "id", entityColumn = "movie_id") var listProductCountry:List<ProductCountry>,
    @Relation(parentColumn = "id", entityColumn = "movie_id") var listSpokenLanguage:List<SpokenLanguage>
){
    constructor():this(Movie(), emptyList(), emptyList(), emptyList())
}