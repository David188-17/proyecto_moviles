package com.nearmusic

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Musica : AppCompatActivity(){


    private lateinit var buttonPlay: Button
    private lateinit var buttonPause: Button
    private lateinit var buttonNext: Button

    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio)

        buttonPlay = findViewById(R.id.buttonPlay)
        buttonPause = findViewById(R.id.buttonPause)
        buttonNext = findViewById(R.id.buttonNext)

        RadioNear()
        Next()


    }

    //PRIMAERA OPCION

    private fun RadioNear() {

        val url = "https://24943.live.streamtheworld.com/CRP_LI_SC?csegid=20001&dist=20001&ttag=20001"; //"https://serverssl.innovatestream.pe:8080/http://167.114.118.120:7442/;";

        mediaPlayer = MediaPlayer()

        mediaPlayer.setDataSource(url)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.prepareAsync()



        mediaPlayer.setOnPreparedListener {

            play(this)
        }
    }

    private fun play(context: Context) {
        buttonPlay.setOnClickListener {
            mediaPlayer.start()
            Toast.makeText(context, "Play Buscando Radio || Reproduciendo... ", Toast.LENGTH_SHORT).show()
        }

        buttonPause.setOnClickListener {
            mediaPlayer.pause()
            Toast.makeText(context, "En Pausa...", Toast.LENGTH_SHORT).show()
        }


    }

    private  fun Next(){
        buttonNext.setOnClickListener {
            val intent = Intent(this, Radio::class.java)
            this.startActivity(intent)
            mediaPlayer.release()
        }

    }}