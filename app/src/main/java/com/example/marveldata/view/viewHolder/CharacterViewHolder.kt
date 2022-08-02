package com.example.marveldata.view.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marveldata.databinding.ItemCardBinding
import com.example.marveldata.model.MarvelCharacter
import javax.inject.Inject

class CharacterViewHolder @Inject constructor(view: View) : RecyclerView.ViewHolder(view) {

     val binding = ItemCardBinding.bind(view)

    fun drawCharacter(character: MarvelCharacter) {

        binding.txtDescription.text = character.description
        binding.txtName.text = character.name
        val fullImage = character.thumbnail.path+"."+character.thumbnail.extension
        Glide.with(binding.imageCharacter.context)
            .load(fullImage)
            .into(binding.imageCharacter)
    }

}