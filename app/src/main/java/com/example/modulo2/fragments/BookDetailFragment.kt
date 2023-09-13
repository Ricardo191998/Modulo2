package com.example.modulo2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.modulo2.R
import com.example.modulo2.application.BooksRFApp
import com.example.modulo2.data.BookRepository
import com.example.modulo2.data.remote.model.BookDetail
import com.example.modulo2.databinding.FragmentBookDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val BOOK_ID = "book_id"

class BookDetailFragment : Fragment() {

    private var bookId: String? = null

    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: BookRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            bookId = args.getString(BOOK_ID)


            repository = (requireActivity().application as BooksRFApp).repository

            lifecycleScope.launch {

                bookId?.let { id ->
                    //val call: Call<GameDetailDto> = repository.getGameDetail(id)
                    val call: Call<BookDetail> = repository.getBooksDetail(id)

                    call.enqueue(object: Callback<BookDetail>{
                        override fun onResponse(
                            call: Call<BookDetail>,
                            response: Response<BookDetail>
                        ) {


                            binding.apply {
                                pbLoading.visibility = View.GONE

                                tvTitle.text = response.body()?.title
                                tvAuthor.text = response.body()?.author
                                tvEditorial.text = response.body()?.editorial
                                tvPages.text = response.body()?.pages
                                tvIsbn.text = response.body()?.isbn

                                Picasso.get()
                                    .load(response.body()?.thumbnail)
                                    .into(ivImage)
                            }

                        }

                        override fun onFailure(call: Call<BookDetail>, t: Throwable) {
                            binding.pbLoading.visibility = View.GONE

                            Toast.makeText(requireActivity(), getString(R.string.alert_error_conection), Toast.LENGTH_SHORT).show()
                        }

                    })
                }

            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(bookId: String) =
            BookDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(BOOK_ID, bookId)
                }
            }
    }

}