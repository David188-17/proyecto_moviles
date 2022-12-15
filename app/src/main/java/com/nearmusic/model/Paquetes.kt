package com.nearmusic.model
import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class Paquetes(

val paquete_economico: String,

val paquete_mediano: String?,

val paquete_grande: String?,

val paquete_premiun: String?

) : Parcelable {

    constructor () :

            this("", "", "", "")
}