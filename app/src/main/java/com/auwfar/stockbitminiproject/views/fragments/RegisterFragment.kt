package com.auwfar.stockbitminiproject.views.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.auwfar.stockbitminiproject.databinding.FragmentRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private var googleSignInClient: GoogleSignInClient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGoogleSignInClient()

        binding.btnLogin.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnRegisterWithEmail.setOnClickListener {
            val toRegisterFormFragment =
                RegisterFragmentDirections.actionRegisterFragmentToRegisterFormFragment(null)
            findNavController().navigate(toRegisterFormFragment)
        }

        binding.btnRegisterWithGoogle.setOnClickListener {
            val intent = googleSignInClient?.signInIntent
            startActivityForResult(intent, RC_GOOGLE_REGISTER)
        }

        binding.btnRegisterWithFacebook.setOnClickListener {
            Toast.makeText(context, "Coming Soon", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupGoogleSignInClient() {
        context?.let {
            googleSignInClient = GoogleSignIn.getClient(
                it,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == RC_GOOGLE_REGISTER) {
            googleSignIn(data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun googleSignIn(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            val toRegisterFormFragment =
                RegisterFragmentDirections.actionRegisterFragmentToRegisterFormFragment(account?.email)
            findNavController().navigate(toRegisterFormFragment)
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        googleSignInClient?.signOut()
    }

    companion object {
        private const val RC_GOOGLE_REGISTER = 100
    }
}