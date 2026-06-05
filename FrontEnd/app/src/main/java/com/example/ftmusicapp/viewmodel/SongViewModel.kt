package com.example.ftmusicapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ftmusicapp.data.model.CreateSongRequest
import com.example.ftmusicapp.data.model.Song
import com.example.ftmusicapp.data.repository.SongRepository
import com.example.ftmusicapp.utils.ApiResult
import com.example.ftmusicapp.utils.UiState
import kotlinx.coroutines.launch

class SongViewModel : ViewModel(){
    private val songRepository = SongRepository()

    private val _songsCatalogState = MutableLiveData<UiState<List<Song>>>(UiState.Idle)
    val songsCatalogState: LiveData<UiState<List<Song>>> = _songsCatalogState

    private val _createSongState = MutableLiveData<UiState<Song>>(UiState.Idle)
    val createSongState: LiveData<UiState<Song>> = _createSongState

    fun getSongsCatalog() {
        viewModelScope.launch {
            _songsCatalogState.value = UiState.Loading

            when (val result = songRepository.getSongsCatalog()) {
                is ApiResult.Success -> {
                    _songsCatalogState.value = UiState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _songsCatalogState.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun createSong(request: CreateSongRequest) {
        viewModelScope.launch {
            _createSongState.value = UiState.Loading

            when (val result = songRepository.createSong(request)) {
                is ApiResult.Success -> {
                    _createSongState.value = UiState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _createSongState.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun resetCreateSongState() {
        _createSongState.value = UiState.Idle
    }
}