package com.globalcorp.taskman.ui.login


import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.globalcorp.taskman.R
import com.globalcorp.taskman.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.firestore.ktx.memoryCacheSettings
import com.google.firebase.firestore.ktx.persistentCacheSettings


class LoginFragment : Fragment() {
    companion object {
        private const val RC_SIGN_IN = 9001 //9001
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel


    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var oneTapClient: SignInClient
    private lateinit var signUpRequest: BeginSignInRequest


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val settings = firestoreSettings {
            // Use memory cache
            setLocalCacheSettings(memoryCacheSettings {})
            // Use persistent disk cache (default)
            setLocalCacheSettings(persistentCacheSettings {})
        }
        db.firestoreSettings = settings

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("462547068807-8nakfa3hr3tsb5jl6rj7la61pukijt0a.apps.googleusercontent.com")
            .requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)


        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        //val account = GoogleSignIn.getLastSignedInAccount(this)
        //updateUI(account)

        val activity = activity as AppCompatActivity
        activity.supportActionBar?.title = "Login"


        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory(auth)).get(LoginViewModel::class.java)

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
                usernameEditText.text.toString(), passwordEditText.text.toString()
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
        }

        binding.loginGoogleLoginButton.setOnClickListener {
            signIn()

        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            val email = account?.email
            val token = account?.idToken
            firebaseAuthWithGoogle(account.idToken!!)

        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user != null) {
                    val docRef = db.collection("users").document(user.uid)
                    docRef.get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val document = task.result
                            if (!document.exists()) {
                                // The user document doesn't exist yet, so create it
                                val newUser = hashMapOf(
                                    "name" to user.displayName,
                                    "email" to user.email,
                                    "userid" to user.uid
                                )
                                docRef.set(newUser)
                            }
                            findNavController().navigate(R.id.action_loginFragment_to_startFragment)
                        } else {
                            Log.d(TAG, "Failed to get user document", task.exception)
                        }
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)

                }
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


        private fun showLoginFailed(@StringRes errorString: Int) {
            val appContext = context?.applicationContext ?: return
            Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }