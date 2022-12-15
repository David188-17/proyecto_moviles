package com.nearmusic.ui.Music
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.nearmusic.Musica
import com.nearmusic.utiles.AudioUtiles
import com.nearmusic.utiles.ImagenUtiles


import com.nearmusic.R
import com.nearmusic.Radio
import com.nearmusic.databinding.FragmentAddMusicBinding
import com.nearmusic.model.Music
import com.nearmusic.viewmodel.MusicViewModel
import java.io.File


class AddMusicFragment : Fragment() {





    private lateinit var musicViewModel: MusicViewModel
    private var _binding: FragmentAddMusicBinding? = null
    private val binding get() = _binding!!

    private lateinit var audioUtiles: AudioUtiles
    private lateinit var imagenUtiles: ImagenUtiles
    private lateinit var tomarFotoActivity: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View {






        musicViewModel =
            ViewModelProvider(this).get(MusicViewModel::class.java)

        _binding = FragmentAddMusicBinding.inflate(inflater, container, false)

        binding.btAdd.setOnClickListener { subeNota() }
        binding.progressBar.visibility = ProgressBar.VISIBLE
        binding.msgMensaje.text = getString(R.string.msg_subiendo_audio)
        binding.msgMensaje.visibility = TextView.VISIBLE


        audioUtiles = AudioUtiles(
            requireActivity(),
            requireContext(),
            binding.btAccion,
            binding.btPlay,
            binding.btDelete,
            getString(R.string.msg_graba_audio),
            getString(R.string.msg_detener_audio)


        )




        tomarFotoActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){

            if (it.resultCode == Activity.RESULT_OK) {
                imagenUtiles.actualizaFoto()

            }

        }

        imagenUtiles = ImagenUtiles(
            requireContext(),
            binding.btPhoto,
            binding.btRotaL,
            binding.btRotaR,
            binding.imagen,
            tomarFotoActivity)


        val intent = Intent(requireContext(), Musica::class.java)
        binding.btradio.setOnClickListener {
            startActivity(intent)
        }

        return binding.root






    }

    private fun subeNota() {

        val archivoLocal = audioUtiles.audioFile
        if(archivoLocal.exists() &&
            archivoLocal.isFile &&
            archivoLocal.canRead()) {

            val rutaLocal = Uri.fromFile(archivoLocal)

            val rutaNube = "MusicApp/${Firebase.auth.currentUser?.email}/audios/${archivoLocal.name}"

            val referencia: StorageReference = Firebase.storage.reference.child(rutaNube)

            referencia.putFile(rutaLocal)
                .addOnSuccessListener {
                    referencia.downloadUrl
                        .addOnSuccessListener {
                            val rutaAudio = it.toString()
                            subeImagen(rutaAudio)
                        }
                }
                .addOnFailureListener{

                    subeImagen("")

                }
        } else {

            subeImagen("")

        }
    }




    private fun subeImagen(rutaAudio: String) {

        binding.msgMensaje.text = getString(R.string.msg_subiendo_imagen)

        val archivoLocal = imagenUtiles.imagenFile
        if(archivoLocal.exists() && archivoLocal.isFile &&
            archivoLocal.canRead()) {

            val rutaLocal = Uri.fromFile(archivoLocal)

            val rutaNube = "MusicApp/${Firebase.auth.currentUser?.email}/imagenes/${archivoLocal.name}"

            val referencia: StorageReference = Firebase.storage.reference.child(rutaNube)

            referencia.putFile(rutaLocal)
                .addOnSuccessListener {
                    referencia.downloadUrl
                        .addOnSuccessListener {
                            val rutaImagen = it.toString()
                            addMusic(rutaAudio,rutaImagen)
                        }
                }
                .addOnFailureListener{

                    addMusic(rutaAudio,"")

                }
        } else {

            addMusic(rutaAudio,"")

        }

    }



     fun addMusic(rutaAudio: String, rutaImagen: String) {
        val nombrecancion = binding.etNombre.text.toString()

        binding.msgMensaje.text = getString(R.string.msg_subiendo_Musica)
        if (nombrecancion.isNotEmpty()) {
            val genero = binding.etgenero.text.toString()
            val artista = binding.etartista.text.toString()
            val anios = binding.etanios.text.toString()



            val music = Music ("", nombrecancion, genero, artista, anios,
                 rutaAudio, rutaImagen)

            musicViewModel.saveMusic(music)
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_Music_added),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_addMusicFragment_to_nav_Music)

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