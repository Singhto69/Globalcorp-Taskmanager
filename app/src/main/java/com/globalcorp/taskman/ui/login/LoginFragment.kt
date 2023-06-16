package com.globalcorp.taskman.ui.login

import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.globalcorp.taskman.R
import com.globalcorp.taskman.databinding.FragmentLoginBinding


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        //auth = Firebase.auth
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(auth))
            .get(LoginViewModel::class.java)

        val usernameEditText = binding.loginUsernameInput
        val passwordEditText = binding.loginPasswordInput
        val loginButton = binding.loginLoginButton
        val registerButton = binding.loginRegisterButton
        val loadingProgressBar = binding.loading

        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        loginButton.setOnClickListener {
            toggleProgressBar(loadingProgressBar)
            auth.signInWithEmailAndPassword(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            ).addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //val user = auth.currentUser
                    findNavController().navigate(R.id.action_loginFragment_to_startFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }

                if (!task.isSuccessful) {

                }
                toggleProgressBar(loadingProgressBar)

            }


            //auth.signOut()

        }
    }

    fun toggleProgressBar(loadingBar: ProgressBar) {
        if (loadingBar.visibility == View.VISIBLE) {
            loadingBar.visibility = View.INVISIBLE
        } else {
            loadingBar.visibility = View.VISIBLE
        }
    }


    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}