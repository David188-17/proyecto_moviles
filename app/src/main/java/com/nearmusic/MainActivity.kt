package com.nearmusic


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nearmusic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var  binding: ActivityMainBinding

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp( this)
        auth = Firebase.auth

        binding.btRegister.setOnClickListener{ haceRegistro() }
        binding.btLogin.setOnClickListener{ haceLogin() }

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_idr))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding.btGoogle.setOnClickListener{ googleSignIn() }

    }

    private fun googleSignIn() {

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 5000)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5000) {

            val tarea = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val cuenta = tarea.getResult(ApiException :: class.java)!!
                firebaseAuthWithGoogle(cuenta.idToken)
            }catch (e: ApiException ){



            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {

        val credenciales = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credenciales)
            .addOnCompleteListener(this) { task ->


                if (task.isSuccessful){
                    Log.d("Autenticando", "Usuario creado")
                    val user = auth.currentUser
                    actualiza(user)
                }else {

                    Log.d("Autenticando", "Error al crear usuario")
                    actualiza(null)
                }

            }
    }

    private fun haceRegistro() {
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        auth.createUserWithEmailAndPassword( email, clave )
            .addOnCompleteListener(this) { task ->
                var user: FirebaseUser? =null

                if (task.isSuccessful){
                    Log.d("Autenticando", "Usuario creado")
                    user = auth.currentUser

                }else {

                    Log.d("Autenticando", "Error al crear usuario")
                }
                actualiza(user)
            }
    }

    private fun haceLogin() {

        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        auth.signInWithEmailAndPassword( email, clave )
            .addOnCompleteListener(this) { task ->
                var user: FirebaseUser? =null

                if (task.isSuccessful){
                    Log.d("Autenticando", "Usuario autenticado")
                    user = auth.currentUser

                }else {

                    Log.d("Autenticando", "Error al autenticar usuario")

                }
                actualiza(user)
            }

    }

    private fun actualiza(user: FirebaseUser?) {

        if (user != null){

            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        actualiza(usuario)
    }

}

