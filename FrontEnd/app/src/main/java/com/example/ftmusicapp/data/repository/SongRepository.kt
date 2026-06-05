package com.example.ftmusicapp.data.repository

import com.example.ftmusicapp.data.api.RetrofitClient
import com.example.ftmusicapp.data.model.CreateSongRequest
import com.example.ftmusicapp.data.model.Song
import com.example.ftmusicapp.utils.ApiResult
import com.example.ftmusicapp.utils.ApiErrorHandler

class SongRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getSongsCatalog(): ApiResult<List<Song>> {
        return try {
            val response = apiService.getSongsCatalog()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null && body.exitoso) {
                    ApiResult.Success(body.data ?: emptyList())
                } else {
                    ApiResult.Error(body?.mensaje ?: "Could not retrieve songs catalog.")
                }
            } else {
                ApiResult.Error(ApiErrorHandler.getErrorMessage(response))
            }
        } catch (ex: Exception) {
            ApiResult.Error(ex.message ?: "Unexpected error getting songs catalog.")
        }
    }

    suspend fun createSong(request: CreateSongRequest): ApiResult<Song> {
        return try {
            val response = apiService.createSong(request)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null && body.exitoso && body.data != null) {
                    ApiResult.Success(body.data)
                } else {
                    ApiResult.Error(body?.mensaje ?: "Could not create song.")
                }
            } else {
                ApiResult.Error(ApiErrorHandler.getErrorMessage(response))
            }
        } catch (ex: Exception) {
            ApiResult.Error(ex.message ?: "Unexpected error creating song.")
        }
    }
}