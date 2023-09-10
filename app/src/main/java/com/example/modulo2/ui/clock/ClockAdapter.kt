package com.example.modulo2.ui.clock

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modulo2.data.clock.data.model.ClockEntity
import com.example.modulo2.databinding.ClockElementBinding
import com.example.modulo2.util.Constants
import com.squareup.picasso.Picasso
import com.example.modulo2.R
import java.time.Clock

class ClockAdapter(private val onClockClick: (ClockEntity) -> Unit) : RecyclerView.Adapter<ClockAdapter.ViewHolder>(){
    private var clocks : List<ClockEntity>? = emptyList()

    class ViewHolder(private val binding: ClockElementBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(clock: ClockEntity){
            binding.apply {
                tvTitle.text = clock.title
                tvBrand.text = getBrand(clock.brand)!!.text
                tvPrice.text = clock.price
            }
            Picasso.get()
                .load(getBrand(clock.brand)!!.imageResource)
                .error(R.drawable.ic_title)
                .placeholder(R.drawable.ic_title)
                .into(binding.ivIcon)
        }

        fun getBrand(brandId: String) : ItemSpinner?{
            for (clock in Constants.CLOCK_ITEMS) {
                if(clock.id == brandId) {
                    return clock
                }
            }
            return null
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ClockElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = clocks!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clocks!![position])

        holder.itemView.setOnClickListener {
            onClockClick(clocks!![position])
        }

    }

    fun updateList(list: List<ClockEntity>){
        clocks = list
        notifyDataSetChanged()
    }
}