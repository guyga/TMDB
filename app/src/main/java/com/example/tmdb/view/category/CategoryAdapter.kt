package com.example.tmdb.view.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ItemCategoryMovieBinding
import com.example.tmdb.network.entities.Movie

class CategoryAdapter(
    private val itemWidth: Int,
    private val movieSelectedListener: MovieSelectedListener,
) : ListAdapter<Movie, CategoryAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.layoutParams.width = itemWidth
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener { movieSelectedListener.onMovieSelected(movie) }
        holder.bind(movie)
    }

    class ViewHolder(var binding: ItemCategoryMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class MovieSelectedListener(val movieSelectedListener: ((movie: Movie) -> Unit)) {
        fun onMovieSelected(movie: Movie) = movieSelectedListener(movie)
    }
}