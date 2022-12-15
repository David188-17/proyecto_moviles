package com.nearmusic.model

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class Favoritos (

    val album_favorito: String,

    val cancion_favorita: String?,

    val categoria_favorita: String?,

    val mas_escuchado: Int?

) : Parcelable {

    constructor () :

            this("", "", "", 0)
}