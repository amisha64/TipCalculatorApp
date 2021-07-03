package com.example.tipcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.getSystemService
import com.example.tipcalculator.databinding.ActivityMainBinding
import kotlin.math.ceil
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculate.setOnClickListener(View.OnClickListener {
            calculateTip()
        })
        binding.etcostinput.setOnKeyListener{view, keyCode,_ -> handleKeyEvent(view, keyCode )}
    }

    private fun calculateTip() {
        val cost = binding.etcostinput.text.toString().toDoubleOrNull()
        if (cost == null){
            binding.tipvalue.text="No tip!"
            return
        }
        val tip = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.rbamazing -> 0.20
            R.id.rbgood -> 0.18
            else -> 0.15
        }
        var tipvalue = tip * cost
        val roundup = binding.switchround.isChecked
        if (roundup) {
            tipvalue = ceil(tipvalue)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tipvalue)
        binding.tipvalue.text = getString(R.string.tip_amount, formattedTip)
    }
    private fun handleKeyEvent(view: View , keyCode:Int):Boolean{
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            //hide keyboard
            val inputMethodManager = getSystemService (Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}