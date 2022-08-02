package com.example.marveldata.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marveldata.data.ApiClient
import com.example.marveldata.model.MarvelCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: ApiClient
) : ViewModel() {

    private val _characters = MutableLiveData<List<MarvelCharacter>>()
    val charactersList: LiveData<List<MarvelCharacter>>
        get() = _characters


    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status


    fun getCharacters() {
        viewModelScope.launch {
            try {
                val response = repository.getSuperHero()
                _characters.value = response.data.results
            } catch (e: Throwable) {
                _characters.value = emptyList()
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