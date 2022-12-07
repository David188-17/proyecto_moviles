package com.nearmusic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nearmusic.data.MusicDao
import com.nearmusic.model.Music
import com.nearmusic.repository.MusicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicViewModel : ViewModel() {

    private val musicRepository: MusicRepository = MusicRepository(MusicDao())
    val getMusices: MutableLiveData<List<Music>> = musicRepository.getMusices

//....


    fun saveMusic(music: Music) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.saveMusic(music)
        }
    }

    fun deleteMusic(music: Music) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.deleteMusic(music)
        }
    }
}