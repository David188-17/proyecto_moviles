package com.nearmusic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nearmusic.databinding.MusicFilaBinding
import com.nearmusic.model.Music

import com.nearmusic.ui.Music.MusicFragmentDirections



class MusicAdapter : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>(){

    inner class MusicViewHolder(private val itemBinding: MusicFilaBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun dibuja(Music: Music) {
            itemBinding.tvNombrecancion.text = Music.nombre_cancion
            itemBinding.tvArtista.text = Music.artista
            itemBinding.tvGenero.text = Music.genero



            Glide.with(itemBinding.root.context)
                .load(Music.rutaImagen)
                .circleCrop()
                .into(itemBinding.imagen)
            itemBinding.vistaFila.setOnClickListener {
                // creo una accion para navegar a updateMusic pasando un argumento Music
                val action= MusicFragmentDirections.actionNavMusicToUpdateMusicFragment(Music)

                //efectivamente se pasa al fragmento...
                itemView.findNavController().navigate(action)
            }
        }
    }

    //La lista donde están los objetos Music a dibujarse...
    private var listaMusices = emptyList<Music>()

    //Esta función crea "cajitas" para cada Music.. en memoria
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val itemBinding = MusicFilaBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        return MusicViewHolder(itemBinding)
    }

    //Esta función toma un Music y lo envia a dibujar...
    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val Music = listaMusices[position]
        holder.dibuja(Music)
    }

    //Esta función devuelve la cantidad de elementos a dibujar...(cajitas)
    override fun getItemCount(): Int {
        return listaMusices.size
    }

    fun setListaMusices(Musices: List<Music>) {
        this.listaMusices = Musices
        notifyDataSetChanged()
    }

}
    
    
    
    
    
    
