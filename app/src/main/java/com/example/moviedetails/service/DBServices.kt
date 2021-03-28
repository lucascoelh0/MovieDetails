package com.example.moviedetails.service

import com.example.moviedetails.dao.GenresDAO
import com.example.moviedetails.database.AppDatabase
import com.example.moviedetails.model.Genres

interface DBRepository {

    //insert
    suspend fun populateGenresDBService()

    //select
    suspend fun getGenreDBService(id: Int): String
    suspend fun getAllGenresDBService(): List<Genres>
    suspend fun isEmptyDBService(): Int
}

class DBRepositoryImplementation(val genresDAO: GenresDAO) : DBRepository {

    override suspend fun populateGenresDBService() {
        val genres = listOf(
            Genres(28, "Ação"),
            Genres(12, "Aventura"),
            Genres(16, "Animação"),
            Genres(35, "Comédia"),
            Genres(80, "Crime"),
            Genres(99, "Documentário"),
            Genres(18, "Drama"),
            Genres(10751, "Família"),
            Genres(14, "Fantasia"),
            Genres(36, "História"),
            Genres(27, "Terror"),
            Genres(10402, "Música"),
            Genres(9648, "Mistério"),
            Genres(10749, "Romance"),
            Genres(878, "Ficção Científica"),
            Genres(10770, "Cinema TV"),
            Genres(53, "Thriller"),
            Genres(10752, "Guerra"),
            Genres(37, "Faroeste"),
        )
        genresDAO.populateGenres(genres)
    }

    override suspend fun getGenreDBService(id: Int): String {
        return genresDAO.getGenre(id)
    }

    override suspend fun getAllGenresDBService(): List<Genres> {
        return genresDAO.getAllGenres()
    }

    override suspend fun isEmptyDBService(): Int {
        return genresDAO.isEmpty()
    }
}

lateinit var dbApp: AppDatabase
lateinit var dbRepository: DBRepository
