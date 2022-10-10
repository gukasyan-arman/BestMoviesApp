package com.example.bestmoviesapp.di

import com.example.bestmoviesapp.data.network.MovieDetailsRepository
import com.example.bestmoviesapp.data.network.MovieService
import com.example.bestmoviesapp.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Modules {

    @Singleton
    @Provides
    fun provideMovieService(): MovieService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @Provides
    fun provideRepository(movieService: MovieService): MovieDetailsRepository {
        return MovieDetailsRepository(movieService)
    }

}