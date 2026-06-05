package com.example.ftmusicapp.data.api

import com.example.ftmusicapp.data.model.ApiResponse
import com.example.ftmusicapp.data.model.AssignSongRequest
import com.example.ftmusicapp.data.model.AssignedSongResponse
import com.example.ftmusicapp.data.model.CreatePlayListRequest
import com.example.ftmusicapp.data.model.CreateSongRequest
import com.example.ftmusicapp.data.model.PlayList
import com.example.ftmusicapp.data.model.Song
import com.example.ftmusicapp.data.model.SongByPlayList
import com.example.ftmusicapp.data.model.UpdatePlayListRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("songs")
    suspend fun getSongsCatalog(): Response<ApiResponse<List<Song>>>

    @POST("songs")
    suspend fun createSong(
        @Body request: CreateSongRequest
    ): Response<ApiResponse<Song>>

    @GET("playlists")
    suspend fun getPlayLists(): Response<ApiResponse<List<PlayList>>>

    @POST("playlists")
    suspend fun createPlayList(
        @Body request: CreatePlayListRequest
    ): Response<ApiResponse<PlayList>>

    @PUT("playlists/{idPlayList}")
    suspend fun updatePlayList(
        @Path("idPlayList") idPlayList: Int,
        @Body request: UpdatePlayListRequest
    ): Response<ApiResponse<PlayList>>

    @DELETE("playlists/{idPlayList}")
    suspend fun deletePlayList(
        @Path("idPlayList") idPlayList: Int
    ): Response<ApiResponse<String>>

    @GET("playlists/{idPlayList}/songs")
    suspend fun getSongsByPlayList(
        @Path("idPlayList") idPlayList: Int
    ): Response<ApiResponse<List<SongByPlayList>>>

    @POST("playlists/{idPlayList}/songs")
    suspend fun addSongToPlayList(
        @Path("idPlayList") idPlayList: Int,
        @Body request: AssignSongRequest
    ): Response<ApiResponse<AssignedSongResponse>>

    @DELETE("playlists/{idPlayList}/songs/{idSong}")
    suspend fun removeSongFromPlayList(
        @Path("idPlayList") idPlayList: Int,
        @Path("idSong") idSong: Int
    ): Response<ApiResponse<String>>
}