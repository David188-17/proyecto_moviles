package com.nearmusic.model
import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class Biblioteca (


    val muscia_usuario: String,

    val categoria_musica_usuario: String?,

    val orden_canciones: String?,

    val cantidad_canciones: Int?

) : Parcelable{

    constructor ():

            this("","","",0)
















}