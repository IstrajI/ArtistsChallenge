package com.example.android.artistchalenge.ui.main.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.artistchalenge.R
import com.example.android.artistchalenge.data.models.Artist
import com.example.android.artistchalenge.databinding.ItemBookmarkBinding
import com.example.android.artistchalenge.ui.main.MainActivityNavigationListener

class BookmarkAdapter : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {
    private var items = mutableListOf<Artist>()
    var clickListener: MainActivityNavigationListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding =
            ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(newItems: List<Artist>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    inner class BookmarkViewHolder(private val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Artist) {
                Glide.with(binding.root).load(item.image).centerCrop()
                    .placeholder(R.drawable.ic_actor_placeholder).into(binding.bookmarkImage)
                binding.bookmarkName.text = item.name
                binding.bookmarkType.text = item.type
                binding.bookmarkView.setOnClickListener {
                    if (clickListener == null || item.mbidId == null) return@setOnClickListener
                    clickListener!!.onActorDetailedClicked(item.mbidId)
                }
            }
    }
}