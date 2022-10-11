package com.example.bestmoviesapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.bestmoviesapp.R
import com.example.bestmoviesapp.databinding.FragmentDetailBinding
import com.example.bestmoviesapp.ui.movie.MainViewModel
import com.example.bestmoviesapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding

    val args: DetailFragmentArgs by navArgs()

    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backPress.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getMovieDetails(args.omdbId!!)

        viewModel.movieDetails.observe(viewLifecycleOwner) {

            when(it.getContentIfNotHandled()?.status){

                Status.LOADING -> {
                    binding.detailProgressBar.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    binding.detailProgressBar.visibility = View.GONE
                }

                Status.SUCCESS -> {
                    binding.detailProgressBar.visibility = View.GONE

                    val data = it.peekContent().data

                    binding.apply {
                        detailMovieActors.text = data?.Actors
                        detailMovieTitle.text = data?.Title
                        detailMovieWriters.text = data?.Writer
                        detailMovieRating.text = data?.imdbRating
                        detailMovieVotes.text = data?.imdbVotes
                        detailMovieReleased.text = data?.Released
                        detailMoviePoster.load(data?.Poster)
                        detailToolBar.text = data?.Title
                    }

                }

                else -> {}

            }

        }

    }

}