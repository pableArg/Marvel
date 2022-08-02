package com.example.marveldata.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marveldata.data.ApiClient
import com.example.marveldata.model.ServerComic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class ComicViewModel
@Inject constructor(
    private val repository: ApiClient
) : ViewModel() {
    private val _comics = MutableLiveData<List<ServerComic>>()
    val comicsList: LiveData<List<ServerComic>>
        get() = _comics



    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status


    fun getComics(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getComics(id)
                _comics.value = response.data.results
            } catch (e: Throwable) {
                _comics.value = emptyList()
                when (e) {
                    is IOException -> _status.value = ApiStatus.IO_EXCEPTION
                    is HttpException -> when (e.code()) {
                        in 400..499 -> _status.value = ApiStatus.ERROR_400
                        in 500..599 -> _status.value = ApiStatus.ERROR_500

                    }

                }


            }
        }

    }


}