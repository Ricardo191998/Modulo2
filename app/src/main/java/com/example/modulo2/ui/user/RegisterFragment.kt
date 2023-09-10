package com.example.modulo2.ui.user

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.modulo2.R
import com.example.modulo2.application.ClocksDBApp
import com.example.modulo2.data.user.UserRepository
import com.example.modulo2.data.user.data.model.UserEntity
import com.example.modulo2.databinding.FragmentRegisterBinding
import com.example.modulo2.ui.clock.ClockActivity
import kotlinx.coroutines.launch
import java.io.IOException


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val aplicacionContext = requireContext().applicationContext
        repository = (aplicacionContext as ClocksDBApp).repositoryUser
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setUpRegister()
        return root
    }

    private fun setUpRegister() {
        val btnRegister = binding.btRegister

        btnRegister.setOnClickListener {

            val etName = binding.etName
            val etEmail = binding.etEmail
            val etPassword = binding.etPassword

            val validationName = validateText(etName, getString(R.string.text_name_required), false)
            val validationEmail = validateText(etEmail, getString(R.string.text_user_required), true)
            val validationPassword = validateText(etPassword, getString(R.string.text_pass_required), false)

            if(validationName && validationEmail && validationPassword) {
                try {
                    lifecycleScope.launch {
                        var newUser: UserEntity = UserEntity(name = etName.text.toString(), email = etEmail.text.toString(), password = etPassword.text.toString())
                        val id = repository.insertUser(newUser)

                        if(id != null){
                            val intent = Intent(context, ClockActivity::class.java).apply {
                                putExtra("USER_ID", id)
                            }
                            startActivity(intent)
                        }
                    }

                    Toast.makeText(requireContext(), getString(R.string.alert_create_user_success) , Toast.LENGTH_SHORT).show()

                } catch (e: IOException){
                    e.printStackTrace()
                    Toast.makeText(requireContext(), getString(R.string.alert_create_user_failed) , Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

    private fun validateText(textView: EditText, message: String, isEmail: Boolean): Boolean{
        val inputText = textView.text.toString().trim()
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") // Expresión regular para validar el formato del correo electrónico

        if (inputText.isEmpty()) {
            textView.error = message
            return false
        } else if(isEmail && !inputText.matches(emailPattern)){
            textView.error = message
            return false
        }else {
            textView.error = null
            textView.setTextColor(Color.BLACK)
            return true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}