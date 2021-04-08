package com.example.tmdb.view.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb.databinding.FragmentDetailsBinding
import com.example.tmdb.details.MovieRepository
import com.example.tmdb.network.entities.Video
import com.example.tmdb.view.MainActivity

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val movie = DetailsFragmentArgs.fromBundle(requireArguments()).movie

        (activity as MainActivity).adjustToolbar(movie.title, movie.getPreviewPath(), true)

        val binding = FragmentDetailsBinding.inflate(inflater)
        val viewModel =
            ViewModelProvider(this, DetailsViewModelFactory(MovieRepository.movieRepo)).get(
                DetailsViewModel::class.java
            )
        binding.viewModel = viewModel
        binding.movie = movie
        binding.lifecycleOwner = viewLifecycleOwner
        binding.castList.adapter = CastAdapter()
        binding.videosList.adapter =
            VideosAdapter(VideosAdapter.VideoClickedListener { openVideo(it) })

        viewModel.fetchMovieDataData(movie.id)

        return binding.root
    }

    private fun openVideo(video: Video) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.getYouTubeVideoUrl()))
        startActivity(intent)
    }
}