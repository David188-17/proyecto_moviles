package com.nearmusic.utiles

import java.text.SimpleDateFormat
import java.util.*

//en esta seccion del codigo se almacena datos en formato de fecha
class OtrosUtiles {
    companion object {
        fun getTempFile(prefijo:String) : String {
            val nombre=SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            return prefijo+nombre
        }
    }
}


