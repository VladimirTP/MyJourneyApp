package com.example.myjourneyapp.ui.register.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myjourneyapp.R
import com.example.myjourneyapp.databinding.FragmentLoginBinding
import com.example.myjourneyapp.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel: LoginViewModel by viewModels()

    lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    signUpViewModel.fireBaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Log.d("My Log", "Api exception")
            }
        }

        binding.btGoogleSignIn.setOnClickListener {
            signUpViewModel.signInWithGoogle(requireActivity(), launcher)
        }

        binding.signupRedirectText.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        binding.loginButton.setOnClickListener {
            signUpViewModel.performLogin(
                binding.loginEmail.text.toString(),
                binding.loginPassword.text.toString()
            )
        }

        signUpViewModel.loginSuccessLiveData.observe(viewLifecycleOwner) {success ->
            if (success) {
                val i = Intent(requireActivity(), MainActivity::class.java)
                startActivity(i)
                Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), getString(R.string.authentication_failed), Toast.LENGTH_SHORT).show()
            }
        }

        signUpViewModel.authenticatedWithGoogle.observe(viewLifecycleOwner) {
            if (it) {
                val i = Intent(requireActivity(), MainActivity::class.java)
                startActivity(i)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}