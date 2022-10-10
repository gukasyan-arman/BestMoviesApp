package com.example.bestmoviesapp.data.model

data class MovieResponse(
    val Response: String,
    val Search: List<Movie>,
    val totalResults: String
)