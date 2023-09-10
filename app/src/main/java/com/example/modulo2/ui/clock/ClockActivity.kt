package com.example.modulo2.ui.clock

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modulo2.R
import com.example.modulo2.application.ClocksDBApp
import com.example.modulo2.data.clock.ClockRepository
import com.example.modulo2.data.clock.data.model.ClockEntity
import com.example.modulo2.databinding.ActivityClockBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ClockActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClockBinding


    private var clocks : List<ClockEntity>? = emptyList()
    private lateinit var repository: ClockRepository
    private var userId by Delegates.notNull<Long>()


    private lateinit var clockAdapter: ClockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClockBinding.inflate(layoutInflater)

        repository = (application as ClocksDBApp).repositoryClock
        setContentView(binding.root)

        userId = intent?.getLongExtra(  "USER_ID", 0)!!

        clockAdapter = ClockAdapter() { clock ->
            val dialog = ClockDialog(clock, false, updateUI = {
                updateUI()
            }, message = { text ->
                message(text)
            })
            dialog.show(supportFragmentManager, "dialog")
        }

        binding.rvClock.layoutManager = LinearLayoutManager(this@ClockActivity)
        binding.rvClock.adapter = clockAdapter

        updateUI()
    }

    private fun updateUI() {
        lifecycleScope.launch {
            clocks = userId?.let { repository.getClocks(it) }

            if(clocks!!.isNotEmpty()){
                //Hay por lo menos un registro
                binding.tvSinRegistros.visibility = View.INVISIBLE
            }else{
                //No hay registros
                binding.tvSinRegistros.visibility = View.VISIBLE
            }
            clockAdapter.updateList(clocks!!)
        }
    }

    fun click(view : View){
        val dialog = ClockDialog(ClockEntity(
            title = "",
            brand = "",
            price = "",
            material = "",
            userId = userId
        ), updateUI = {
            updateUI()
        }, message = { text ->
            message(text)
        })
        dialog.show(supportFragmentManager, "dialog")
    }

    private fun message(text: String){
        Snackbar.make(binding.cl, text, Snackbar.LENGTH_SHORT)
            .setTextColor(Color.parseColor("#FFFFFF"))
            .setBackgroundTint(Color.parseColor("#9E1734"))
            .show()
    }
}