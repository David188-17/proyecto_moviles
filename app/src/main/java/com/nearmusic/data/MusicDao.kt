package com.nearmusic.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase

import com.nearmusic.model.Music

//aca procedimos a crear el music dao
class MusicDao {

    //variables usadas para poder generar la estructura en la nube

    private val coleccion1 = "musicesApp"
   private val usuario = Firebase.auth.currentUser?.email.toString()
   private val coleccion2 = "mismusices"

    //obtiene la conexion a la base de datos
    private var firestore: FirebaseFirestore =FirebaseFirestore.getInstance()

    init{
        //inicia la configuracion de firestore
        firestore.firestoreSettings =FirebaseFirestoreSettings.Builder().build()


    }
     fun saveMusic(music: Music){
         //Para definir un documento en la nube.

val documento : DocumentReference

if (music.id.isEmpty()) {//si esta vacio es un nuevo documento...
documento =firestore.

collection(coleccion1)

    .document(usuario)
    .collection(coleccion2)
    .document()
    music.id = documento.id

}else{//si el id tiene algo... entonces se va a modificar ese documento(music)
    documento =firestore.

    collection(coleccion1)

        .document(usuario)
        .collection(coleccion2)
        .document()


}
         //ahora se modifica o crea el documento.
         documento.set(music)
             .addOnCompleteListener{
                 Log.d("saveMusic","Music creado/actualizado")
             }
             .addOnCompleteListener{
                 Log.e("saveMusic","Music no crwado/actualizado")
             }
    }





     fun deleteMusic(music: Music) {
         // se valida si el music tiene id... para poder borrarlo:)
         if (music.id.isEmpty()) {//si  no esta vacio ... se puede eliminar
             firestore.
             collection(coleccion1)

                 .document(usuario)
                 .collection(coleccion2)
                 .document(music.id)

                 .delete()

                 .addOnCompleteListener{
                     Log.d("deleteMusic","Music eliminado")
                 }
                 .addOnCompleteListener{
                     Log.e("deleteMusic","Music No eliminado")
                 }
         }
     }

    fun getMusices() : MutableLiveData<List<Music>> {
val listaMusices = MutableLiveData<List<Music>>()

        firestore.
        collection(coleccion1)

            .document(usuario)
            .collection(coleccion2)
            .addSnapshotListener { instantanea, e ->
                if (e != null) {//se dio un error ....capturando la imagen de info
                    return@addSnapshotListener

                }
                //si estamos aca .. no hubo error..
                if (instantanea != null) { //si se pudo recuperar la info...
                    val lista = ArrayList<Music>()
// se recorre a instantanea documento por documento... convirtiendolo en Music y agregandolo en la lista
                    instantanea.documents.forEach {
                        val music = it.toObject(Music::class.java)
                        if (music != null) {// si se pudo convertir el documento en un music
                            lista.add(music) //se agrega el music a la lista...


                        }
                    }
                    listaMusices.value = lista
                }
            }//**
        return listaMusices


    }

}