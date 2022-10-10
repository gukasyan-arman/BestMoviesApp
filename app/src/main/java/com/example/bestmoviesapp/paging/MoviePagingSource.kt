package com.example.bestmoviesapp.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bestmoviesapp.data.model.Movie
import com.example.bestmoviesapp.data.network.MovieService
import com.example.bestmoviesapp.utils.API_KEY
import java.lang.Exception
import java.lang.reflect.Executable

class MoviePagingSource(
        val s: String,
        val movieService: MovieService
    ): PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {

            val data = movieService.getAllMovies(s, page, API_KEY)

            LoadResult.Page (
                data = data.body()?.Search!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.body()?.Search?.isEmpty()!!) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}