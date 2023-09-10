package com.example.modulo2.ui.clock

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.modulo2.R
import com.squareup.picasso.Picasso

class SpinnerAdapter (context: Context, resource: Int, items: List<ItemSpinner>) :
    ArrayAdapter<ItemSpinner>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.spinner_item, parent, false)

        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val textView = itemView.findViewById<TextView>(R.id.textView)

        val item = getItem(position)
        if (item != null) {
            Log.i("I", item.imageResource)
            Picasso.get()
                .load(item.imageResource)
                .error(R.drawable.ic_edit)
                .placeholder(R.drawable.ic_title)
                .into(imageView)
            textView.text = item.text
        }

        return itemView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}