package com.nearmusic.model
import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class Estaciones  (

    val baladas304: String,

    val pop_latino212: String?,

    val criolla190: String?,

    val pop_internacional181: String?

) : Parcelable {

    constructor () :

            this("", "", "", "")
}