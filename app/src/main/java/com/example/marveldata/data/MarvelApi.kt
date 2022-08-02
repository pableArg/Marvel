package com.example.marveldata.data

import com.example.marveldata.model.CharacterResponse
import com.example.marveldata.model.ComicDataWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigInteger
import java.security.MessageDigest

const val PRIVATE_KEY = "fb9fecbe049d4156287734094a7fc7f42426e369"
const val PUBLIC_KEY = "36d6e66d8e369f1a6ef8d3358fd9810c"

interface MarvelApi {

    @GET("characters")
    suspend fun getCharacters (
        @Query("ts") ts: String,
        @Query("apikey") apikey: String = PUBLIC_KEY,
        @Query("hash") hash: String = md5(ts + PRIVATE_KEY + PUBLIC_KEY)
    ) :CharacterResponse



    @GET("characters/{characterId}/comics")
    suspend fun getComics(
        @Path(value = "characterId", encoded = true) characterId: Int,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String = PUBLIC_KEY,
        @Query("hash") hash: String = md5(ts + PRIVATE_KEY + PUBLIC_KEY)
    ): ComicDataWrapper

}
fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}