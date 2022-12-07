package com.nearmusic.ui.Music

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nearmusic.R
import com.nearmusic.adapter.MusicAdapter
import com.nearmusic.databinding.FragmentMusicBinding
import com.nearmusic.viewmodel.MusicViewModel


class MusicFragment : Fragment() {

    private var _binding: FragmentMusicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val MusicViewModel =
            ViewModelProvider(this)[MusicViewModel::class.java]

        _binding = FragmentMusicBinding.inflate(inflater, container, false)

        binding.addMusicFabButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_Music_to_addMusicFragment)
        }

        //Se genera el recicler view para ver la información...
        val MusicAdapter= MusicAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter = MusicAdapter
        reciclador.layoutManager= LinearLayoutManager(requireContext())

        MusicViewModel.getMusices.observe(viewLifecycleOwner) {
                Musices -> MusicAdapter.setListaMusices(Musices)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}