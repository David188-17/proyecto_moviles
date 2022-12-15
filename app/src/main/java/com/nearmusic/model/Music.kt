package com.nearmusic.model

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
//en el model es donde se establecen datos y que tipos de datos van a ser
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
