package com.nearmusic.model
import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuarios_Premium (


    val nombre_premium: String,
    //
    val correo_premium: String?,

    val telefono_premium: String?,

    val cedula_premium: Int?

    ) : Parcelable{

        constructor ():

        this("","","",0)

    }

