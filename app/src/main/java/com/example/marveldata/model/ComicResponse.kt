package com.example.marveldata.model

data class ComicDataWrapper(
    val code: Int,
    val status: String,
    val data: ComicDataContainer
)
data class ComicDataContainer(
    val results: List<ServerComic>
)
data class ServerComic(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: ThumbnailComic
)

data class ThumbnailComic(
    val path: String,
    val extension: String
)