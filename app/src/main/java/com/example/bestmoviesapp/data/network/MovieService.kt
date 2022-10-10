package com.example.bestmoviesapp.data.network

import com.example.bestmoviesapp.data.model.MovieDetails
import com.example.bestmoviesapp.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/")
    suspend fun getAllMovies(
        @Query("s")s:String,
        @Query("page")page:Int,
        @Query("apiKey")apiKey:String
    ): Response<MovieResponse>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i")omdbId:String,
        @Query("apiKey")apiKey:String
    ): Response<MovieDetails>

}