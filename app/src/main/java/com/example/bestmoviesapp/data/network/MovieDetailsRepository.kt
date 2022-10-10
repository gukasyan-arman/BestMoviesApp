package com.example.bestmoviesapp.data.network

import com.example.bestmoviesapp.data.model.MovieDetails
import com.example.bestmoviesapp.utils.API_KEY
import com.example.bestmoviesapp.utils.Result
import com.example.bestmoviesapp.utils.Status

class MovieDetailsRepository(private val movieService: MovieService) {


    suspend fun getMovieDetails(imdbId: String): Result<MovieDetails> {
        return try {
            val response = movieService.getMovieDetails(imdbId, API_KEY)
            if (response.isSuccessful) {

                Result(Status.SUCCESS, response.body(), null)

            } else {

                Result(Status.ERROR, null, null)

            }
        } catch (e: Exception) {

            Result(Status.ERROR, null, null)

        }
    }


}