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
import com.auwfar.stockbitminiproject.databinding.FragmentLoginBinding
import com.auwfar.stockbitminiproject.utils.Utils
import com.auwfar.stockbitminiproject.viewmodels.UsersViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var googleSignInClient: GoogleSignInClient? = null
    private val usersViewModel by viewModel<UsersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGoogleSignInClient()
        observeUsers()

        binding.btnLoginWithGoogle.setOnClickListener {
            val intent = googleSignInClient?.signInIntent
            startActivityForResult(intent, RC_GOOGLE_SIGN_IN)
        }

        binding.btnRegister.setOnClickListener {
            val toRegisterFragment = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(toRegisterFragment)
        }

        binding.btnLogin.setOnClickListener {
            Utils.hideKeyboard(context, binding.editPassword)

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isBlank()) {
                Toast.makeText(context, "Email tidak boleh kosong", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if (password.isBlank()) {
                Toast.makeText(context, "Password tidak boleh kosong", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            usersViewModel.login(email, password)
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

    private fun observeUsers() {
        usersViewModel.isUserFound.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn == true) {
                Toast.makeText(context, "Anda Berhasil login!", Toast.LENGTH_LONG).show()
                val toCryptoFragment = LoginFragmentDirections.actionLoginFragmentToCryptoFragment()
                findNavController().navigate(toCryptoFragment)
            } else {
                Toast.makeText(context, "User tidak ditemukan, silahkan daftar terlebih dahulu", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_GOOGLE_SIGN_IN) {
                googleSignIn(data)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun googleSignIn(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            account?.email?.let { email ->
                usersViewModel.getUserWithEmail(email)
            }
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        googleSignInClient?.signOut()
    }

    companion object {
        private const val RC_GOOGLE_SIGN_IN = 100
    }
}