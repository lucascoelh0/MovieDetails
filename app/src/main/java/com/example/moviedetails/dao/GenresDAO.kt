package com.example.moviedetails.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviedetails.model.Genres

@Dao
interface GenresDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun populateGenres(genres: List<Genres>)

    @Query("SELECT name FROM genres WHERE id = :id")
    suspend fun getGenre(id: Int): String

    @Query("SELECT * FROM genres")
    suspend fun getAllGenres(): List<Genres>

    @Query("SELECT max(id) FROM genres")
    suspend fun isEmpty(): Int
}