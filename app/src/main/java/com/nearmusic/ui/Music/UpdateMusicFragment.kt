package com.nearmusic.ui.Music


import android.app.AlertDialog

import android.media.MediaPlayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nearmusic.R
import com.nearmusic.databinding.FragmentUpdateMusicBinding
import com.nearmusic.model.Music
import com.nearmusic.viewmodel.MusicViewModel


class UpdateMusicFragment : Fragment() {
    private val args by navArgs<UpdateMusicFragmentArgs>()

    private lateinit var musicViewModel: MusicViewModel
    private var _binding: FragmentUpdateMusicBinding? = null
    private val binding get() = _binding!!

    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        musicViewModel =
            ViewModelProvider(this).get(MusicViewModel::class.java)

        _binding = FragmentUpdateMusicBinding.inflate(inflater, container, false)


        binding.etNombre.setText(args.music.nombre_cancion)
        binding.etgenero.setText(args.music.genero)
        binding.etartista.setText(args.music.artista)
        binding.etanios.setText(args.music.año)


        binding.btUpdate.setOnClickListener { updateMusic() }
        binding.btDelete.setOnClickListener { deleteMusic() }



        if (args.music.rutaAudio?.isNotEmpty()==true) {

            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(args.music.rutaAudio)
            mediaPlayer.prepare()
           binding.btPlay.isEnabled=true

        } else {
           binding.btPlay.isEnabled=false

        }

        binding.btPlay.setOnClickListener{mediaPlayer.start()}

        if (args.music.rutaImagen?.isNotEmpty()==true) {

            Glide.with(requireContext())
                .load(args.music.rutaImagen)
                .fitCenter()
                .into(binding.imagen)

        }

        return binding.root
    }


    private fun deleteMusic() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.bt_delete_Music)
        alerta.setMessage(getString(R.string.msg_pregunta_delete) + " ${args.music.nombre_cancion}? ")
        alerta.setPositiveButton(getString(R.string.msg_si)) { _, _ ->
            musicViewModel.deleteMusic(args.music)
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_Music_deleted),
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_updateMusicFragment_to_nav_Music)

        }
        alerta.setNegativeButton(getString(R.string.msg_no)) { _, _ ->

        }
        alerta.create().show()
    }

    private fun updateMusic() {
        val nombrecancion = binding.etNombre.text.toString()

        if (nombrecancion.isNotEmpty()) {
            val genero = binding.etgenero.text.toString()
            val artista = binding.etartista.text.toString()
            val anios = binding.etanios.text.toString()
            val music = Music(
                args.music.id,
                nombrecancion,
                genero,
                artista,
                anios,

                args.music.rutaAudio,
                args.music.rutaImagen
            )

            musicViewModel.saveMusic(music)
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_Music_updated),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateMusicFragment_to_nav_Music)

        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_data),
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}