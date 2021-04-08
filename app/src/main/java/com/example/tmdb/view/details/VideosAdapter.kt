package com.example.tmdb.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ItemVideoBinding
import com.example.tmdb.network.entities.Video

class VideosAdapter(val videoClickedListener: VideoClickedListener) :
    ListAdapter<Video, VideosAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = getItem(position)
        holder.itemView.setOnClickListener { videoClickedListener.onVideoClicked(video) }
        holder.bind(video)
    }

    class ViewHolder(var binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video) {
            binding.video = video
            binding.executePendingBindings()
        }
    }

    class VideoClickedListener(val videoClickedListener: ((video: Video) -> Unit)) {
        fun onVideoClicked(video: Video) = videoClickedListener(video)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.id == newItem.id
        }
    }
}