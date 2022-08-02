package com.example.marveldata.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marveldata.R
import com.example.marveldata.model.ServerComic
import com.example.marveldata.view.viewHolder.ComicViewHolder
import javax.inject.Inject

class ComicAdapter @Inject constructor(
    var comicList: List<ServerComic>,


    ) : RecyclerView.Adapter<ComicViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComicViewHolder(
            layoutInflater.inflate(R.layout.item_comic, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val item = comicList[position]
        holder.drawCharacter(item)

    }


    override fun getItemCount(): Int = comicList.size

}