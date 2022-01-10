package com.example.android.artistchalenge.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.artistchalenge.R
import com.example.android.artistchalenge.data.models.Artist
import com.example.android.artistchalenge.databinding.ItemSearchBinding
import com.example.android.artistchalenge.ui.main.MainActivityNavigationListener

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private var items = mutableListOf<Artist>()
    var clickListener: MainActivityNavigationListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(newItems: List<Artist>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Artist) {
            Glide.with(binding.root).load(item.image).centerCrop()
                .placeholder(R.drawable.ic_actor_placeholder).into(binding.searchImage)
            binding.searchName.text = item.name
            binding.searchType.text = item.type
            binding.rootView.setOnClickListener {
                if (clickListener == null || item.id == null) return@setOnClickListener
                clickListener!!.onActorDetailedClicked(item.id)
            }
        }
    }
}