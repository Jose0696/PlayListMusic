package com.example.ftmusicapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ftmusicapp.data.model.AssignSongRequest
import com.example.ftmusicapp.data.model.AssignedSongResponse
import com.example.ftmusicapp.data.model.CreatePlayListRequest
import com.example.ftmusicapp.data.model.PlayList
import com.example.ftmusicapp.data.model.SongByPlayList
import com.example.ftmusicapp.data.model.UpdatePlayListRequest
import com.example.ftmusicapp.data.repository.PlayListRepository
import com.example.ftmusicapp.utils.ApiResult
import com.example.ftmusicapp.utils.UiState
import kotlinx.coroutines.launch

class PlayListViewModel: ViewModel() {

    private val playListRepository = PlayListRepository()

    private val _playListsState = MutableLiveData<UiState<List<PlayList>>>(UiState.Idle)
    val playListsState: LiveData<UiState<List<PlayList>>> = _playListsState

    private val _createPlayListState = MutableLiveData<UiState<PlayList>>(UiState.Idle)
    val createPlayListState: LiveData<UiState<PlayList>> = _createPlayListState

    private val _updatePlayListState = MutableLiveData<UiState<PlayList>>(UiState.Idle)
    val updatePlayListState: LiveData<UiState<PlayList>> = _updatePlayListState

    private val _deletePlayListState = MutableLiveData<UiState<String>>(UiState.Idle)
    val deletePlayListState: LiveData<UiState<String>> = _deletePlayListState

    private val _songsByPlayListState = MutableLiveData<UiState<List<SongByPlayList>>>(UiState.Idle)
    val songsByPlayListState: LiveData<UiState<List<SongByPlayList>>> = _songsByPlayListState

    private val _addSongToPlayListState = MutableLiveData<UiState<AssignedSongResponse>>(UiState.Idle)
    val addSongToPlayListState: LiveData<UiState<AssignedSongResponse>> = _addSongToPlayListState

    private val _removeSongFromPlayListState = MutableLiveData<UiState<String>>(UiState.Idle)
    val removeSongFromPlayListState: LiveData<UiState<String>> = _removeSongFromPlayListState

    fun getPlayLists() {
        viewModelScope.launch {
            _playListsState.value = UiState.Loading

            when (val result = playListRepository.getPlayLists()) {
                is ApiResult.Success -> {
                    _playListsState.value = UiState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _playListsState.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun createPlayList(request: CreatePlayListRequest) {
        viewModelScope.launch {
            _createPlayListState.value = UiState.Loading

            when (val result = playListRepository.createPlayList(request)) {
                is ApiResult.Success -> {
                    _createPlayListState.value = UiState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _createPlayListState.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun updatePlayList(idPlayList: Int, request: UpdatePlayListRequest) {
        viewModelScope.launch {
            _updatePlayListState.value = UiState.Loading

            when (val result = playListRepository.updatePlayList(idPlayList, request)) {
                is ApiResult.Success -> {
                    _updatePlayListState.value = UiState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _updatePlayListState.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun deletePlayList(idPlayList: Int) {
        viewModelScope.launch {
            _deletePlayListState.value = UiState.Loading

            when (val result = playListRepository.deletePlayList(idPlayList)) {
                is ApiResult.Success -> {
                    _deletePlayListState.value = UiState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _deletePlayListState.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun getSongsByPlayList(idPlayList: Int) {
        viewModelScope.launch {
            _songsByPlayListState.value = UiState.Loading

            when (val result = playListRepository.getSongsByPlayList(idPlayList)) {
                is ApiResult.Success -> {
                    _songsByPlayListState.value = UiState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _songsByPlayListState.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun addSongToPlayList(idPlayList: Int, request: AssignSongRequest) {
        viewModelScope.launch {
            _addSongToPlayListState.value = UiState.Loading

            when (val result = playListRepository.addSongToPlayList(idPlayList, request)) {
                is ApiResult.Success -> {
                    _addSongToPlayListState.value = UiState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _addSongToPlayListState.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun removeSongFromPlayList(idPlayList: Int, idSong: Int) {
        viewModelScope.launch {
            _removeSongFromPlayListState.value = UiState.Loading

            when (val result = playListRepository.removeSongFromPlayList(idPlayList, idSong)) {
                is ApiResult.Success -> {
                    _removeSongFromPlayListState.value = UiState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _removeSongFromPlayListState.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun resetCreatePlayListState() {
        _createPlayListState.value = UiState.Idle
    }

    fun resetUpdatePlayListState() {
        _updatePlayListState.value = UiState.Idle
    }

    fun resetDeletePlayListState() {
        _deletePlayListState.value = UiState.Idle
    }

    fun resetAddSongToPlayListState() {
        _addSongToPlayListState.value = UiState.Idle
    }

    fun resetRemoveSongFromPlayListState() {
        _removeSongFromPlayListState.value = UiState.Idle
    }
}