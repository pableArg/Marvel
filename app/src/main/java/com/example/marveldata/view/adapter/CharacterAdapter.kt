package com.example.marveldata.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marveldata.R
import com.example.marveldata.model.MarvelCharacter
import com.example.marveldata.view.fragments.CharacterFragmentDirections
import com.example.marveldata.view.viewHolder.CharacterViewHolder
import javax.inject.Inject

class CharacterAdapter @Inject constructor(
    var characterList: List<MarvelCharacter>,


    ) : RecyclerView.Adapter<CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(
            layoutInflater.inflate(R.layout.item_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = characterList[position]
        holder.drawCharacter(item)
        holder.binding.cv.setOnClickListener {
            val action = CharacterFragmentDirections.actionCharacterFragmentToComicFragment(
                item.id
            )
            holder.binding.cv.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = characterList.size


}
