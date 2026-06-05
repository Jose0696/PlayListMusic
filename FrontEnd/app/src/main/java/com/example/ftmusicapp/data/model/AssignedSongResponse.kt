package com.example.ftmusicapp.data.model

data class AssignedSongResponse(
    val idPlayListSong: Int,
    val idPlayList: Int,
    val idSong: Int,
    val title: String,
    val artist: String,
    val album: String?,
    val genre: String?,
    val lapseTime: String?,
    val assignmentDate: String?
)