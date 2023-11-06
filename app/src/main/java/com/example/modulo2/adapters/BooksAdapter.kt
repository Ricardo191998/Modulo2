package com.example.modulo2.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.modulo2.R
import com.example.modulo2.data.remote.model.Book
import com.example.modulo2.databinding.BookElementBinding
import com.squareup.picasso.Picasso

class BooksAdapter(
    private val books: List<Book>,
    private val onBookClicked: (Book) -> Unit
): RecyclerView.Adapter<BooksAdapter.ViewHolder>(){
    class ViewHolder(private val binding: BookElementBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(book: Book, context: Context){
            binding.tvTitle.text = book.title
            binding.tvAuthor.text = book.author

            val imageView: ImageView = itemView.findViewById(R.id.ivThumbnail)
            Log.d("LOSGS", book.thumbnail.toString())
            /*Picasso.get()
                .load(book.thumbnail.toString())
                .into(binding.ivThumbnail)*/
            Glide.with(context)
                .load(book.thumbnail)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]

        holder.bind(book, holder.itemView.context)

        //Procesamiento del clic al elemento
        holder.itemView.setOnClickListener {
            onBookClicked(book)
        }
    }
}