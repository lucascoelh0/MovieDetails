package com.example.moviedetails.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviedetails.dao.GenresDAO
import com.example.moviedetails.model.Genres

@Database(entities = [Genres::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun genresDAO(): GenresDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_details.db"
        ).build()
    }
}