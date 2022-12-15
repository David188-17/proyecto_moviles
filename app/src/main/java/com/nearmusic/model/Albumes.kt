package com.nearmusic.model
import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class Albumes
    (

    val album_masvisto: String,

    val album_maslike: String?,

    val album_masodiado: String?,

    val pop_internacional181: String?

) : Parcelable {

    constructor () :

            this("", "", "", "")
}