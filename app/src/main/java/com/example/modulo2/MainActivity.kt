package com.example.modulo2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.modulo2.databinding.ActivityMainBinding
import com.example.modulo2.fragments.BooksListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BooksListFragment())
                .commit()
        }
    }
}