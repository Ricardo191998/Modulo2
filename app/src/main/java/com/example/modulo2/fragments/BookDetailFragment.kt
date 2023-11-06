package com.example.modulo2.fragments

import android.net.Uri
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
import android.media.MediaPlayer
import kotlinx.coroutines.internal.artificialFrame


private const val BOOK_ID = "book_id"

class BookDetailFragment : Fragment() {

    private var bookId: String? = null

    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: BookRepository
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaPlayer = MediaPlayer.create(requireActivity(), R.raw.lord_rings)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)


        binding.fab.setOnClickListener {
            mediaPlayer?.start()
        }

        arguments?.let { args ->
            bookId = args.getString(BOOK_ID)

            repository = (requireActivity().application as BooksRFApp).repository

            lifecycleScope.launch {

                bookId?.let { id ->
                    //val call: Call<GameDetailDto> = repository.getGameDetail(id)
                    if( id == "1") {
                        binding.fab.visibility = View.VISIBLE
                    } else {
                        binding.fab.visibility = View.GONE
                    }

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

                                val videoUri = Uri.parse(response.body()?.videoUrl)
                                vvVideo.setVideoURI(videoUri)
                                vvVideo.setOnPreparedListener { mediaplayer ->
                                    //pbVideo.visibility = View.GONE
                                    mediaplayer.start()
                                    val videoRatio: Float = mediaplayer.videoWidth / mediaplayer.videoHeight.toFloat()
                                    val screenRatio: Float = vvVideo.width / vvVideo.height.toFloat()
                                    val scale: Float = videoRatio / screenRatio

                                    if(scale>=1){
                                        vvVideo.scaleX = scale
                                    }else{
                                        vvVideo.scaleY = 1f/scale
                                    }
                                    //Para que se cambie al completarse
                                    vvVideo.setOnCompletionListener {mediaplayer ->
                                        mediaplayer.start()
                                    }
                                }
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