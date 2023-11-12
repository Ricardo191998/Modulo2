package com.example.modulo2.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modulo2.LoginActivity
import com.example.modulo2.R
import com.example.modulo2.adapters.BooksAdapter
import com.example.modulo2.application.BooksRFApp
import com.example.modulo2.data.BookRepository
import com.example.modulo2.data.remote.model.Book
import com.example.modulo2.databinding.FragmentBooksListBinding
import com.example.modulo2.util.Constants
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksListFragment : Fragment() {

    private var _binding: FragmentBooksListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: BookRepository
    private var mediaPlayer: MediaPlayer? = null

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBooksListBinding.inflate(inflater, container, false)

        mediaPlayer = MediaPlayer.create(requireActivity(), R.raw.whip)

        firebaseAuth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = (requireActivity().application as BooksRFApp).repository

        binding.btnCerrarSesion.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        lifecycleScope.launch {
            val call: Call<List<Book>> = repository.getBooks()

            call.enqueue(object: Callback<List<Book>>{
                override fun onResponse(
                    call: Call<List<Book>>,
                    response: Response<List<Book>>
                ) {
                    binding.pbLoading.visibility = View.GONE

                    response.body()?.let{ books ->
                        binding.rvBooks.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = BooksAdapter(books){ book ->
                                mediaPlayer?.start()
                                book.id?.let { id ->
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, BookDetailFragment.newInstance(id))
                                        .addToBackStack(null)
                                        .commit()
                                }
                            }
                        }
                    }

                }

                override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                    Log.d(Constants.LOGTAG, "Error: ${t.message}")
                    Toast.makeText(requireActivity(), getString(R.string.alert_error_conection), Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = BooksListFragment()
    }
}