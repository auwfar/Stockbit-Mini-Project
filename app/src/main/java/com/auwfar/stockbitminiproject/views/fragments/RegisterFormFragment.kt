package com.auwfar.stockbitminiproject.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.auwfar.stockbitminiproject.R
import com.auwfar.stockbitminiproject.databinding.FragmentRegisterFormBinding
import com.auwfar.stockbitminiproject.local.entities.UserEntity
import com.auwfar.stockbitminiproject.utils.Utils
import com.auwfar.stockbitminiproject.viewmodels.UsersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFormFragment : Fragment() {
    private lateinit var binding: FragmentRegisterFormBinding
    private var email: String? = null
    private val usersViewModel by viewModel<UsersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = RegisterFormFragmentArgs.fromBundle(it).argEmail
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUsers()

        email?.let {
            binding.editEmail.setText(it)
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnRegister.setOnClickListener {
            Utils.hideKeyboard(context, binding.editNewPasswordConfirmation)

            val email = binding.editEmail.text.toString()
            val password = binding.editNewPassword.text.toString()
            val passwordConfirmation = binding.editNewPasswordConfirmation.text.toString()

            if (email.isBlank() || password.isBlank() || passwordConfirmation.isBlank()) {
                Toast.makeText(context, "Email, Password dan Konfirmasi Password tidak boleh kosong", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if (password != passwordConfirmation) {
                Toast.makeText(context, "Password dan Konfirmasi Password harus sama", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else {
                usersViewModel.getUserWithEmail(email)
            }
        }
    }

    private fun observeUsers() {
        usersViewModel.isUserFound.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(context, "Email sudah terdaftar", Toast.LENGTH_LONG).show()
            } else {
                registerUser()
            }
        }

        usersViewModel.registerResult.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(context, "Register berhasil, silahkan login", Toast.LENGTH_LONG).show()
                findNavController().popBackStack(R.id.loginFragment, false)
            } else {
                Toast.makeText(context, "Gagal mendaftarkan user", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun registerUser() {
        val email = binding.editEmail.text.toString()
        val password = binding.editNewPassword.text.toString()
        usersViewModel.registerUser(UserEntity(email, password))
    }
}