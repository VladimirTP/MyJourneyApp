package com.example.myjourneyapp.ui.register.fragments

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myjourneyapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var _loginSuccessLiveData = MutableLiveData<Boolean>()
    val loginSuccessLiveData: LiveData<Boolean> get() = _loginSuccessLiveData

    private val _authenticatedWithGoogle = MutableLiveData<Boolean>()
    val authenticatedWithGoogle: LiveData<Boolean> get() = _authenticatedWithGoogle

    private var auth: FirebaseAuth = Firebase.auth

    fun performLogin(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplication(), getApplication<Application>().getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show()
            _loginSuccessLiveData.postValue(false)
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginSuccessLiveData.postValue(true)
                } else {
                    _loginSuccessLiveData.postValue(false)
                }
            }
    }

    fun fireBaseAuthWithGoogle (idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful) {
                Log.d("My Log", "Google signIn done")
                checkAuthState()
                _authenticatedWithGoogle.postValue(true)
            } else {
                Log.d("My Log", "Google signIn error")
                _authenticatedWithGoogle.postValue(false)
            }
        }
    }

    private fun checkAuthState() {
        if (auth.currentUser != null) {
            _authenticatedWithGoogle.postValue(true)
        }
    }

    private fun getClient(activity: Activity): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getApplication<Application>().getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(activity, gso)
    }

    fun signInWithGoogle(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
    ) {
        val signInClient = getClient(activity)
        launcher.launch(signInClient.signInIntent)
        checkAuthState()
    }
}