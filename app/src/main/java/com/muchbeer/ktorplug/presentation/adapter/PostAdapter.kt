package com.muchbeer.ktorplug.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muchbeer.ktorplug.data.db.CgrievanceEntity
import com.muchbeer.ktorplug.databinding.ListItemBinding

class PostAdapter : ListAdapter<CgrievanceEntity, PostAdapter.PostVH>(DifUtilCalBack) {

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
            fun bind(data : CgrievanceEntity) {
                binding.itemTitle.text = data.title
                binding.itemDescription.text = data.body
            }
    }

    companion object DifUtilCalBack : DiffUtil.ItemCallback<CgrievanceEntity>() {
        override fun areItemsTheSame(oldItem: CgrievanceEntity, newItem: CgrievanceEntity): Boolean {
            return oldItem.id == newItem.id      }

        override fun areContentsTheSame(
            oldItem: CgrievanceEntity,
            newItem: CgrievanceEntity
        ) : Boolean{ return oldItem == newItem     }
    }


}