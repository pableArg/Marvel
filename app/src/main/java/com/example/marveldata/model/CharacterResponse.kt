package com.example.marveldata.model


data class CharacterResponse(
    val data: ResponseData
)

data class ResponseData(
    val results: List<MarvelCharacter>
)

data class MarvelCharacter(
    var id: Int,
    val name: String,
    val description: String,
    val thumbnail: CharacterImage,
    val comics: ComicList
)


data class ComicList(
    val available: Int,
    val returned: Int,
    val items: List<ComicSummary>? = null
)

data class ComicSummary(
    val resourceURI: String,
    val name: String
)


data class CharacterImage(
    val path: String?,
    val extension: String?
)

