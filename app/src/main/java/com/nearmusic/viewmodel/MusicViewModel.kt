package com.nearmusic.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nearmusic.data.MusicDao
import com.nearmusic.model.Music
import com.nearmusic.repository.MusicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicViewModel (application: Application) : AndroidViewModel(application) {

    private val musicRepository: MusicRepository = MusicRepository(MusicDao())
    val getMusic: MutableLiveData<List<Music>> = musicRepository.getMusic

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