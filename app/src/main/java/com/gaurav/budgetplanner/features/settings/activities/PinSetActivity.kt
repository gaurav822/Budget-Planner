package com.gaurav.budgetplanner.features.settings.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.CustomPinKeyboard
import com.gaurav.budgetplanner.databinding.PinSetFragmentBinding

class PinSetActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding:PinSetFragmentBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = PinSetFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners(){
        binding.keyboard.setListener(object : CustomPinKeyboard.Listener {
            override fun onLongClickListener() {
                binding.walletPinEditText.setText("")
            }

            override fun onBackClickListener() {
                onBackPressed()
            }

            override fun onRequestFocus() {
                binding.walletPinEditText.requestFocus()
            }

           override fun onFingerPrintCLick() {
//                showFingerPrint();
//                fingerPrintHandler.init()
            }
        })
    }

    override fun onClick(view: View) {

    }
}