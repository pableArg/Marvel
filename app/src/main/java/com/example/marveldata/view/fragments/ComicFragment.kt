package com.example.marveldata.view.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marveldata.R
import com.example.marveldata.databinding.FragmentComicBinding
import com.example.marveldata.model.ServerComic
import com.example.marveldata.view.adapter.ComicAdapter
import com.example.marveldata.view.viewModel.ApiStatus
import com.example.marveldata.view.viewModel.ComicViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComicFragment : Fragment() {
    private var _binding: FragmentComicBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ComicViewModel by activityViewModels()
    private lateinit var comicAdapter: ComicAdapter
    val args: ComicFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle("Marvel List")

        setUpObserver()


    }

    private fun setUpObserver() {
        setComics()
        viewModel.comicsList.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                snackBar()
            } else {
                initRecyclerView(it)


            }
        }
    }


    @SuppressLint("SwitchIntDef")
    private fun initRecyclerView(data: List<ServerComic>) {
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                setSpanCount(4, data)

            }
            Configuration.ORIENTATION_PORTRAIT -> {
                setSpanCount(2, data)

            }
        }


    }

    private fun setSpanCount(spanCount: Int, data: List<ServerComic>) {
        binding.rv.layoutManager = GridLayoutManager(this.context, spanCount)
        comicAdapter = ComicAdapter(
            data
        )
        binding.rv.adapter = comicAdapter

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


    private fun setComics() {
        viewModel.getComics(getIdCharacter())

    }

    private fun getIdCharacter(): Int {
        var idCharacter = 0
        idCharacter = args.id
        return idCharacter
    }


}