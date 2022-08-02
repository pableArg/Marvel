package com.example.marveldata.data

import com.example.marveldata.model.CharacterResponse
import com.example.marveldata.model.ComicDataWrapper
import java.util.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class ApiClient @Inject constructor(
    private val marvelApi: MarvelApi
) {
    private val timeStamp = Date().time.milliseconds

    suspend fun getSuperHero(): CharacterResponse {
        return marvelApi.getCharacters(timeStamp.toString())
    }
    suspend fun getComics(characterId: Int) : ComicDataWrapper {
        return marvelApi.getComics( characterId,timeStamp.toString())
    }


}