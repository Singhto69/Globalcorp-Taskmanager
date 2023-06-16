package com.globalcorp.taskman.ui.login

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.globalcorp.taskman.databinding.FragmentRegisterBinding

import com.globalcorp.taskman.R
import com.google.firebase.auth.FirebaseAuth
import kotlin.reflect.jvm.internal.impl.types.TypeCheckerState.SupertypesPolicy.None

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val usernameEditText = binding.registerUsernameEdittext
        val passwordEditText1 = binding.registerPasswordEdittext1
        val passwordEditText2 = binding.registerPasswordEdittext2
        val registerButton = binding.registerRegisterButton
        val loadingProgressBar = binding.registerLoading

        //  sendPasswordResetEmail
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val text1 = passwordEditText1.text.toString()
                val text2 = passwordEditText2.text.toString()

                if (text1 == text2 && usernameEditText.text.toString().isNotEmpty()) {
                    registerButton.isEnabled = true
                }
            }
        }
        passwordEditText1.addTextChangedListener(textWatcher)
        passwordEditText2.addTextChangedListener(textWatcher)


        registerButton.setOnClickListener {
            toggleProgressBar(loadingProgressBar)

            auth.createUserWithEmailAndPassword(
                usernameEditText.text.toString(), passwordEditText1.text.toString()
            ).addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Succces! check your email",
                        Toast.LENGTH_SHORT,
                    ).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

                } else {
                    Toast.makeText(
                        context,
                        "Error, try again",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
                toggleProgressBar(loadingProgressBar)
            }
        }

    }


    fun toggleProgressBar(loadingBar: ProgressBar) {
        if (loadingBar.visibility == View.VISIBLE) {
            loadingBar.visibility = View.INVISIBLE
        } else {
            loadingBar.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

