package com.example.modulo2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modulo2.data.remote.model.Book
import com.example.modulo2.databinding.BookElementBinding
import com.squareup.picasso.Picasso

class BooksAdapter(
    private val books: List<Book>,
    private val onBookClicked: (Book) -> Unit
): RecyclerView.Adapter<BooksAdapter.ViewHolder>(){
    class ViewHolder(private val binding: BookElementBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(book: Book){
            binding.tvTitle.text = book.title
            binding.tvAuthor.text = book.author
            Picasso.get()
                .load(book.thumbnail)
                .into(binding.ivThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]

        holder.bind(book)

        //Procesamiento del clic al elemento
        holder.itemView.setOnClickListener {
            onBookClicked(book)
        }
    }
}