package by.home.testtaskmovie.dataManager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.home.testtaskmovie.dataManager.dao.*
import by.home.testtaskmovie.dataManager.entities.*


@Database(entities = [Movie::class,Genre::class,ProductCountry::class,ProductCompany::class,SpokenLanguage::class], version = 1,exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun movieDao():MovieDao
    abstract fun movieDetailsDao():MovieDetailsDao
    abstract fun genreDao():GenreDao
    abstract fun productCountryDao():ProductCountryDao
    abstract fun productCompanyDao():ProductCompanyDao
    abstract fun spokenLanguageDao():SpokenLanguageDao

    companion object {

        private const val NAME_DATABASE="db-movie"

        private var INSTANCE: RoomDB? = null

        fun getInstance(context: Context): RoomDB{
            if(INSTANCE==null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    NAME_DATABASE
                ).build()
            }
            return INSTANCE!!
        }
    }
}