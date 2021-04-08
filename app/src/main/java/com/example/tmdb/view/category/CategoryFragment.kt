package com.example.tmdb.view.category

import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.FragmentCategoryBinding
import com.example.tmdb.view.Category
import com.example.tmdb.view.MainActivity


class CategoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val category =
            Category.values()[CategoryFragmentArgs.fromBundle(requireArguments()).categoryOrdinal]

        (activity as MainActivity).adjustToolbar(getString(category.titleRes), null, true)

        val binding = FragmentCategoryBinding.inflate(inflater)
        val viewModel =
            ViewModelProvider(this).get(CategoryViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val itemWidth =
            getScreenWidth() / (binding.list.layoutManager as GridLayoutManager).spanCount

        binding.list.adapter = CategoryAdapter(
            itemWidth,
            CategoryAdapter.MovieSelectedListener { viewModel.displayMovieDetails(it) })
        viewModel.fetchCategory(category)

        viewModel.navigateToMovieDetails.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                findNavController().navigate(
                    CategoryFragmentDirections.actionCategoryFragmentToDetailsFragment(
                        it
                    )
                )
                viewModel.displayMovieDetailsComplete()
            }
        })

        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.onCategoryBottomReached()
                }
            }
        })

        return binding.root
    }

    private fun getScreenWidth(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = requireActivity().windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }
}