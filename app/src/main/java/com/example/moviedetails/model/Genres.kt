package com.example.moviedetails.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genres(
    @PrimaryKey
    val id: Int,
    val name: String
)

