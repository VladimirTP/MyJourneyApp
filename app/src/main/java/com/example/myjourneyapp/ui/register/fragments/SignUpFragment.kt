package com.example.myjourneyapp.ui.register.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myjourneyapp.R
import com.example.myjourneyapp.databinding.FragmentSignUpBinding
import com.example.myjourneyapp.ui.MainActivity

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginRedirectText.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        binding.signupButton.setOnClickListener {
            with(binding) {
                signUpViewModel.performSignUp(
                    signupEmail.text.toString(),
                    signupPassword.text.toString(),
                    signupConfirmPassword.text.toString()
                )
            }
        }

        signUpViewModel.signUpSuccessLiveData.observe(viewLifecycleOwner) { success ->
            if (success) {
                val i = Intent(requireActivity(), MainActivity::class.java)
                startActivity(i)
                Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.authentication_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}