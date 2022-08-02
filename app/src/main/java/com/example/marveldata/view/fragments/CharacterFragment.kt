package com.example.marveldata.view.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marveldata.R
import com.example.marveldata.databinding.FragmentCharacterBinding
import com.example.marveldata.model.MarvelCharacter
import com.example.marveldata.view.adapter.CharacterAdapter
import com.example.marveldata.view.viewModel.ApiStatus
import com.example.marveldata.view.viewModel.CharacterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterFragment : Fragment() {
    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterViewModel by activityViewModels()
    private lateinit var characterAdapter: CharacterAdapter
    private val characterList = mutableListOf<MarvelCharacter>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitleAppBar("Marvel Character")

        setCharacters()
        setUpObserver()
    }


    private fun initRecyclerView(data: List<MarvelCharacter>) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val manager =
                GridLayoutManager(binding.root.context, 2, GridLayoutManager.VERTICAL, false)
            binding.rv.layoutManager = manager
        } else {
            binding.rv.layoutManager = LinearLayoutManager(context)
            binding.rv.layoutManager = LinearLayoutManager(binding.root.context)
        }
        characterAdapter = CharacterAdapter(data)
        binding.rv.adapter = characterAdapter


    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setUpObserver() {
        viewModel.charactersList.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                snackBar()
            } else {
              initRecyclerView(it)

            }
        }
    }



    private fun snackBar() {
        var statusResponse: String
        viewModel.status.observe(viewLifecycleOwner) {
            statusResponse = when (it) {
                ApiStatus.ERROR_400 -> getString(R.string.error_400)
                ApiStatus.ERROR_500 -> getString(R.string.error_500)
                ApiStatus.IO_EXCEPTION -> getString(R.string.error_io_exception)
            }
            Snackbar.make(binding.rv, statusResponse, Snackbar.LENGTH_LONG)
                .setAction(R.string.actionText) {
                    setUpObserver()
                }
                .show()
        }
    }

    private fun setCharacters() {
        viewModel.getCharacters()
    }
    private fun setTitleAppBar(title : String){
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(title)
    }


}