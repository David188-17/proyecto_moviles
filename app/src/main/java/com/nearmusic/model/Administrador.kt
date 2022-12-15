package com.nearmusic.model


import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize

data class Administrador(


val nombre_admin: String,

val correo_admin: String?,

val telefono_admin: String?,

val cedula_admin: Int?

) : Parcelable{

    constructor ():

    this("","","",0)

}









