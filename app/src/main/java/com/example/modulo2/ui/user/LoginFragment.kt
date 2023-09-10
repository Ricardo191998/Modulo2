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
import com.example.modulo2.databinding.FragmentLoginBinding
import com.example.modulo2.ui.clock.ClockActivity
import kotlinx.coroutines.launch
import java.io.IOException


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val btnRegister = binding.btnLoginRegister


        btnRegister.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager

            val fragmentB = RegisterFragment()

            val transaction = fragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_login, fragmentB)

            transaction.addToBackStack(null)

            transaction.commit()
        }

        setupLogin()

        return root
    }

    private fun setupLogin() {
        val btnLogin = binding.btLogin

        btnLogin.setOnClickListener {
            val tvEmail = binding.etEmailLogin
            val tvPassword = binding.etLoginPassword
            val validationEmail = validateText(tvEmail, getString(R.string.text_user_required))
            val validationPassword = validateText(tvPassword, getString(R.string.text_pass_required))

            if(validationEmail && validationPassword) {
                try {
                    lifecycleScope.launch {
                        val user = repository.getUser(tvEmail.text.trim().toString())

                        if(user != null){
                            Log.i("T", user.toString())
                            if(user.password == tvPassword.text.trim().toString()){
                                val intent = Intent(context, ClockActivity::class.java).apply {
                                    putExtra("USER_ID", user.id)
                                }
                                startActivity(intent)
                            } else {
                                Toast.makeText(requireContext(),getString(R.string.alert_pass_incorrect) , Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(),getString(R.string.alert_user_not_valid) , Toast.LENGTH_SHORT).show()
                        }
                    }

                }catch (e: IOException){
                    //e.printStackTrace()
                    Toast.makeText(requireContext(),getString(R.string.alert_create_user_failed) , Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun validateText(textView: EditText, message: String): Boolean {
        val inputText = textView.text.toString().trim()

        if (inputText.isEmpty()) {
            textView.error = message
            return false
        }

        textView.error = null
        textView.setTextColor(Color.BLACK)
        return true

    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}