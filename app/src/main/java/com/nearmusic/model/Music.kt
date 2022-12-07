package com.nearmusic.model

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize

data class Music(

    var id:String,

    val nombre_cancion: String,
//
    val genero: String?,

    val año: String?,
    val artista: String?,

    val rutaAudio: String?,

    val rutaImagen: String?
    ) : Parcelable{

        constructor ():

                this("","","","","","","")

    }
