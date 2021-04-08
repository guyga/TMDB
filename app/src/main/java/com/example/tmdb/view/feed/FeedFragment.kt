package com.example.tmdb.view.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentFeedBinding
import com.example.tmdb.view.Category

class FeedFragment : Fragment(), View.OnClickListener {

    private lateinit var _viewModel: FeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(inflater)

        _viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = _viewModel

        val onMovieSelectedListener =
            FeedAdapter.MovieSelectedListener { _viewModel.displayMovieDetails(it) }

        val itemWidthSmall = resources.getDimensionPixelSize(R.dimen.item_width_small)
        val itemWidthBig = resources.getDimensionPixelSize(R.dimen.item_width_big)
        binding.nowPlayingList.adapter = FeedAdapter(itemWidthBig, onMovieSelectedListener)
        binding.popularMoviesList.adapter = FeedAdapter(itemWidthSmall, onMovieSelectedListener)
        binding.topRatedList.adapter = FeedAdapter(itemWidthSmall, onMovieSelectedListener)
        binding.upcomingList.adapter = FeedAdapter(itemWidthSmall, onMovieSelectedListener)

        _viewModel.navigateToMovieDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(
                    FeedFragmentDirections.actionMainFragmentToDetailsFragment(
                        it
                    )
                )
                _viewModel.displayMovieDetailsComplete()
            }
        })

        _viewModel.navigateToCategory.observe(viewLifecycleOwner, Observer {
            if (it >= 0) {
                findNavController().navigate(
                    FeedFragmentDirections.actionMainFragmentToCategoryFragment(
                        it
                    )
                )
                _viewModel.displayCategoryComplete()
            }
        })

        binding.categoryNowPlaying.setOnClickListener(this)
        binding.categoryPopular.setOnClickListener(this)
        binding.categoryTopRated.setOnClickListener(this)
        binding.categoryUpcoming.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.categoryNowPlaying -> {
                _viewModel.displayCategory(Category.NOW_PLAYING.ordinal)
            }
            R.id.categoryPopular -> {
                _viewModel.displayCategory(Category.POPULAR_MOVIES.ordinal)
            }
            R.id.categoryTopRated -> {
                _viewModel.displayCategory(Category.TOP_RATED_MOVIES.ordinal)
            }
            R.id.categoryUpcoming -> {
                _viewModel.displayCategory(Category.UPCOMING_MOVIES.ordinal)
            }
        }
    }
}