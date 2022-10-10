package com.example.bestmoviesapp.ui.movie

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.bestmoviesapp.data.model.MovieDetails
import com.example.bestmoviesapp.data.network.MovieDetailsRepository
import com.example.bestmoviesapp.data.network.MovieService
import com.example.bestmoviesapp.paging.MoviePagingSource
import com.example.bestmoviesapp.utils.Events
import com.example.bestmoviesapp.utils.Result
import com.example.bestmoviesapp.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieService: MovieService,
    private val repository: MovieDetailsRepository
): ViewModel() {

    private val query = MutableLiveData<String>("")

    val list = query.switchMap {query ->
        Pager(PagingConfig(pageSize = 10)) {
            MoviePagingSource(query, movieService)
        }.liveData.cachedIn(viewModelScope)
    }

    fun setQuery(s: String) {
        query.postValue(s)
    }

    private val _movieDetails = MutableLiveData<Events<Result<MovieDetails>>>()
    val movieDetails: LiveData<Events<Result<MovieDetails>>> = _movieDetails

    fun getMovieDetails(imdbId: String) = viewModelScope.launch {
        _movieDetails.postValue(Events(Result(Status.LOADING, null, null)))
        _movieDetails.postValue(Events(repository.getMovieDetails(imdbId)))
    }

}