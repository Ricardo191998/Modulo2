package com.example.modulo2.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.modulo2.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentManager: FragmentManager = supportFragmentManager

        val fragment: Fragment = LoginFragment()

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_login, fragment)

        transaction.commit()
        setContentView(R.layout.activity_login)
    }
}