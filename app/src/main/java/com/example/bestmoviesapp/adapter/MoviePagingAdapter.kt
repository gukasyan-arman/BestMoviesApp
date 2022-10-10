package com.example.bestmoviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.bestmoviesapp.R
import com.example.bestmoviesapp.data.model.Movie
import com.example.bestmoviesapp.databinding.MovieItemBinding

class MoviePagingAdapter: PagingDataAdapter<Movie, MoviePagingAdapter.MyViewHolder>(diffCallback) {

    var onClick: ((String) -> Unit) ?= null

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    fun onMovieClick(listener: (String) -> Unit) {
        onClick = listener
    }

    inner class MyViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.binding.apply {
            movieTitle.text = currentItem?.Title
            movieYear.text = currentItem?.Year
            moviePoster.load(currentItem?.Poster) {
                crossfade(true)
                crossfade(500)
                error(R.drawable.ic_launcher_foreground)
            }
        }

        holder.binding.root.setOnClickListener {
            onClick?.let {
                it(currentItem?.imdbID!!)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

}