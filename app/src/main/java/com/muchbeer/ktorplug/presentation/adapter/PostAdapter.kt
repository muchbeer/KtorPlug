package com.muchbeer.ktorplug.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import com.muchbeer.ktorplug.databinding.ListItemBinding

class PostAdapter : ListAdapter<PostResponseDto, PostAdapter.PostVH>(DifUtilCalBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH {
        val inflater = LayoutInflater.from(parent.context)
       val binding = ListItemBinding.inflate(inflater, parent, false)
        return PostVH(binding)
    }

    override fun onBindViewHolder(holder: PostVH, position: Int) {
       val responstDto = getItem(position)
        holder.bind(responstDto)
    }

    class PostVH (private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
            fun bind(data : PostResponseDto) {
                binding.itemTitle.text = data.title
                binding.itemDescription.text = data.body
            }
    }

    companion object DifUtilCalBack : DiffUtil.ItemCallback<PostResponseDto>() {
        override fun areItemsTheSame(oldItem: PostResponseDto, newItem: PostResponseDto): Boolean {
            return oldItem.id == newItem.id      }

        override fun areContentsTheSame(
            oldItem: PostResponseDto,
            newItem: PostResponseDto
        ) : Boolean{ return oldItem == newItem     }
    }


}