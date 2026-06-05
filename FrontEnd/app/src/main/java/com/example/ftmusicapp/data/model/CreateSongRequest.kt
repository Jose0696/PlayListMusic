package com.example.ftmusicapp.data.model

data class CreateSongRequest(
    val title: String,
    val artist: String,
    val album: String?,
    val genre: String?,
    val lapseTime: String?
)