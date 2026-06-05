package com.example.ftmusicapp.data.model

data class SongByPlayList(
    val idSong: Int,
    val title: String,
    val artist: String,
    val album: String?,
    val genre: String?,
    val lapseTime: String?,
    val assignmentDate: String?
)