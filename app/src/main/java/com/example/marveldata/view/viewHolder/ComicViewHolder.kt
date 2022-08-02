package com.example.marveldata.view.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marveldata.databinding.ItemComicBinding
import com.example.marveldata.model.ServerComic
import javax.inject.Inject

class ComicViewHolder @Inject constructor(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemComicBinding.bind(view)

    fun drawCharacter(comic: ServerComic) {
        binding.titleComic.text = comic.title

        val image = comic.thumbnail.path+"."+comic.thumbnail.extension
        Glide.with(binding.imageBook.context)
            .load(image)
            .into(binding.imageBook)
    }


}
