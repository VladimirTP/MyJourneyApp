package com.example.myjourneyapp.ui.register.fragments

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myjourneyapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val _signUpSuccessLiveData = MutableLiveData<Boolean>()
    val signUpSuccessLiveData: LiveData<Boolean> get() = _signUpSuccessLiveData

    private var auth: FirebaseAuth = Firebase.auth

    fun performSignUp(email: String, password: String, confirmPassword: String) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getApplication(), getApplication<Application>().getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show()
            _signUpSuccessLiveData.postValue(false)
            return
        } else if (password != confirmPassword) {
            Toast.makeText(getApplication(), getApplication<Application>().getString(R.string.passwords_do_not_match), Toast.LENGTH_SHORT).show()
            _signUpSuccessLiveData.postValue(false)
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _signUpSuccessLiveData.postValue(true)
                } else {
                    _signUpSuccessLiveData.postValue(false)
                }
            }
    }
}