package com.example.ftmusicapp.data.repository

import com.example.ftmusicapp.data.api.RetrofitClient
import com.example.ftmusicapp.data.model.AssignSongRequest
import com.example.ftmusicapp.data.model.AssignedSongResponse
import com.example.ftmusicapp.data.model.CreatePlayListRequest
import com.example.ftmusicapp.data.model.PlayList
import com.example.ftmusicapp.data.model.SongByPlayList
import com.example.ftmusicapp.data.model.UpdatePlayListRequest
import com.example.ftmusicapp.utils.ApiResult
import com.example.ftmusicapp.utils.ApiErrorHandler

class PlayListRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getPlayLists(): ApiResult<List<PlayList>> {
        return try {
            val response = apiService.getPlayLists()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null && body.exitoso) {
                    ApiResult.Success(body.data ?: emptyList())
                } else {
                    ApiResult.Error(body?.mensaje ?: "Could not retrieve playlists.")
                }
            } else {
                ApiResult.Error(ApiErrorHandler.getErrorMessage(response))
            }
        } catch (ex: Exception) {
            ApiResult.Error(ex.message ?: "Unexpected error getting playlists.")
        }
    }

    suspend fun createPlayList(request: CreatePlayListRequest): ApiResult<PlayList> {
        return try {
            val response = apiService.createPlayList(request)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null && body.exitoso && body.data != null) {
                    ApiResult.Success(body.data)
                } else {
                    ApiResult.Error(body?.mensaje ?: "Could not create playlist.")
                }
            } else {
                ApiResult.Error(ApiErrorHandler.getErrorMessage(response))
            }
        } catch (ex: Exception) {
            ApiResult.Error(ex.message ?: "Unexpected error creating playlist.")
        }
    }

    suspend fun updatePlayList(
        idPlayList: Int,
        request: UpdatePlayListRequest
    ): ApiResult<PlayList> {
        return try {
            val response = apiService.updatePlayList(idPlayList, request)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null && body.exitoso && body.data != null) {
                    ApiResult.Success(body.data)
                } else {
                    ApiResult.Error(body?.mensaje ?: "Could not update playlist.")
                }
            } else {
                ApiResult.Error(ApiErrorHandler.getErrorMessage(response))
            }
        } catch (ex: Exception) {
            ApiResult.Error(ex.message ?: "Unexpected error updating playlist.")
        }
    }

    suspend fun deletePlayList(idPlayList: Int): ApiResult<String> {
        return try {
            val response = apiService.deletePlayList(idPlayList)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null && body.exitoso) {
                    ApiResult.Success(body.data ?: body.mensaje)
                } else {
                    ApiResult.Error(body?.mensaje ?: "Could not delete playlist.")
                }
            } else {
                ApiResult.Error(ApiErrorHandler.getErrorMessage(response))
            }
        } catch (ex: Exception) {
            ApiResult.Error(ex.message ?: "Unexpected error deleting playlist.")
        }
    }

    suspend fun getSongsByPlayList(idPlayList: Int): ApiResult<List<SongByPlayList>> {
        return try {
            val response = apiService.getSongsByPlayList(idPlayList)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null && body.exitoso) {
                    ApiResult.Success(body.data ?: emptyList())
                } else {
                    ApiResult.Error(body?.mensaje ?: "Could not retrieve songs by playlist.")
                }
            } else {
                ApiResult.Error(ApiErrorHandler.getErrorMessage(response))
            }
        } catch (ex: Exception) {
            ApiResult.Error(ex.message ?: "Unexpected error getting songs by playlist.")
        }
    }

    suspend fun addSongToPlayList(
        idPlayList: Int,
        request: AssignSongRequest
    ): ApiResult<AssignedSongResponse> {
        return try {
            val response = apiService.addSongToPlayList(idPlayList, request)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null && body.exitoso && body.data != null) {
                    ApiResult.Success(body.data)
                } else {
                    ApiResult.Error(body?.mensaje ?: "Could not assign song to playlist.")
                }
            } else {
                ApiResult.Error(ApiErrorHandler.getErrorMessage(response))
            }
        } catch (ex: Exception) {
            ApiResult.Error(ex.message ?: "Unexpected error assigning song to playlist.")
        }
    }

    suspend fun removeSongFromPlayList(
        idPlayList: Int,
        idSong: Int
    ): ApiResult<String> {
        return try {
            val response = apiService.removeSongFromPlayList(idPlayList, idSong)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null && body.exitoso) {
                    ApiResult.Success(body.data ?: body.mensaje)
                } else {
                    ApiResult.Error(body?.mensaje ?: "Could not remove song from playlist.")
                }
            } else {
                ApiResult.Error(ApiErrorHandler.getErrorMessage(response))
            }
        } catch (ex: Exception) {
            ApiResult.Error(ex.message ?: "Unexpected error removing song from playlist.")
        }
    }
}