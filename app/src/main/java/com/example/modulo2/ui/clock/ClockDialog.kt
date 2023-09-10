package com.example.modulo2.ui.clock

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.modulo2.R
import com.example.modulo2.application.ClocksDBApp
import com.example.modulo2.data.clock.ClockRepository
import com.example.modulo2.data.clock.data.model.ClockEntity
import com.example.modulo2.databinding.ClockDialogBinding
import com.example.modulo2.util.Constants
import kotlinx.coroutines.launch
import java.io.IOException

class ClockDialog (
    private var clock: ClockEntity = ClockEntity(
        title = "",
        brand = "",
        price = "",
        material = "",
        userId = 0
    ),
    private val newClock: Boolean = true,
    private var updateUI : () -> Unit,
    private val message: (String) -> Unit
    ): DialogFragment() {
    private var _binding : ClockDialogBinding? = null;
    private val binding get() = _binding!!
    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: Dialog
    private lateinit var repository: ClockRepository
    private var saveButton: Button? = null
    private lateinit var selectedBrand : ItemSpinner

    private lateinit var context : Context

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        context = requireContext()

        _binding = ClockDialogBinding.inflate(requireActivity().layoutInflater)

        repository = (requireContext().applicationContext as ClocksDBApp).repositoryClock

        builder = AlertDialog.Builder(requireContext())

        var btnAccept : String
        var btnDelete : String
        var alertSuccess : String
        var alertError : String
        if (newClock) {
            btnAccept = getString(R.string.btn_save)
            btnDelete = getString(R.string.btn_cancel)
            alertSuccess= getString(R.string.alert_message_success)
            alertError = getString(R.string.alert_message_save_success)
        } else {
            btnAccept = getString(R.string.btn_update)
            btnDelete = getString(R.string.btn_delete)
            alertSuccess= getString(R.string.alert_message_udpate_success)
            alertError = getString(R.string.alert_message_udpate_error)
        }
        dialog = builder.setView(binding.root)
            .setTitle( getString(R.string.dialog_title))
            .setPositiveButton(btnAccept, DialogInterface.OnClickListener { dialog, which ->
                clock.title = binding.tietTitle.text.toString()
                clock.price = binding.tietPrice.text.toString()
                clock.material = binding.tietMaterial.text.toString()
                clock.brand = selectedBrand.id

                try {
                    lifecycleScope.launch {
                        if(newClock){
                            repository.insertClock(clock)
                        } else {
                            repository.updateClock(clock)
                        }
                    }

                    Toast.makeText(requireContext(),alertSuccess , Toast.LENGTH_SHORT).show()
                    updateUI()
                }catch(e: IOException){
                    e.printStackTrace()
                    Toast.makeText(requireContext(),alertError , Toast.LENGTH_SHORT).show()
                }
            })
            .setNegativeButton(btnDelete, DialogInterface.OnClickListener { _, _ ->

                try {
                    if(!newClock){
                        AlertDialog.Builder(context)
                            .setTitle(getString(R.string.alert_delete_title))
                            .setMessage("${getString(R.string.alert_delete_desc)}${clock.title}?")
                            .setPositiveButton(getString(R.string.alert_delete_btn)){ _,_ ->
                                try {
                                    lifecycleScope.launch {
                                        repository.deleteCloc(clock)
                                    }
                                    //message(getString(R.string.alert_delete_success))
                                    updateUI()

                                }catch(e: IOException){
                                    e.printStackTrace()
                                    //message(getString(R.string.alert_delete_error))
                                }
                            }
                            .setNegativeButton(getString(R.string.alert_delete_btn_cancel)){ dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                    }
                } catch(e: IOException){
                    e.printStackTrace()
                    message(getString(R.string.alert_delete_error))
                }

            })
            .create()

        setupSpinner()

        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //Se llama después de que se muestra el diálogo en pantalla
    override fun onStart() {
        super.onStart()

        val alertDialog = dialog as AlertDialog //Lo usamos para poder emplear el método getButton (no lo tiene el dialog)
        saveButton = alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
        saveButton?.isEnabled = false

        binding.tietTitle.setText(clock.title)
        binding.tietPrice.setText(clock.price)
        binding.tietMaterial.setText(clock.material)

        binding.tietTitle.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled = validateFields()
            }
        })

        binding.tietPrice.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled = validateFields()
            }

        })

        binding.tietMaterial.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled = validateFields()
            }

        })

    }

    private fun setupSpinner() {
        val spinner = binding.spinner
        val items = Constants.CLOCK_ITEMS
        val adapter = SpinnerAdapter( requireActivity(), R.layout.spinner_item, items)
        var index = 0
        spinner.adapter = adapter

        if(!newClock) {
            items.forEachIndexed { indice, elemento ->
                if(elemento.id == clock.brand){
                    index = indice
                }
            }
            spinner.setSelection(index)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedBrand = parent?.getItemAtPosition(position)!! as ItemSpinner
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun validateFields() =
        (binding.tietTitle.text.toString().isNotEmpty() && binding.tietPrice.text.toString()
            .isNotEmpty() && binding.tietMaterial.text.toString().isNotEmpty())
}