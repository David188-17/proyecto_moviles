package com.nearmusic.repository

import androidx.lifecycle.MutableLiveData
import com.nearmusic.data.MusicDao
import com.nearmusic.model.Music

class MusicRepository(private val musicDao: MusicDao) {

   fun saveMusic(music: Music) {

            musicDao.saveMusic(music)
///***

    }

     fun deleteMusic(music: Music) {

            musicDao.deleteMusic(music)
        }


    val getMusices : MutableLiveData<List<Music>> = musicDao.getMusices()
}