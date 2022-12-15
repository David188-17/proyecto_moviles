package com.nearmusic.repository

import androidx.lifecycle.MutableLiveData
import com.nearmusic.data.MusicDao
import com.nearmusic.model.Music
// aca se genero el music repository  donde se utiliza musica dao y las funciones save and delete
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