package com.gaurav.budgetplanner.features.settings.activities

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.CustomPinKeyboard
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.Views.HomeScreenActivity
import com.gaurav.budgetplanner.databinding.PinSetActivityBinding
import com.gaurav.budgetplanner.features.converter.presentation.activities.CurrencyConvertActivity
import com.swifttechnology.imepay.Views.Utils.CirclePinEdittext.PinField.OnTextCompleteListener

class PinSetActivity : BaseActivity(), View.OnClickListener {
    private var _binding:PinSetActivityBinding?=null
    private val binding get() = _binding!!
    private var isFirstAttempt = true
    private var firstAttemptPin = ""
    private var isFromSplash:Boolean?=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = PinSetActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init(){
       isFromSplash = intent.extras?.getBoolean("isFromSplash")
        binding.tvError.text = getString(R.string.choose_a_pin)
        binding.toolbarPin.apply {
            toolbarIconLayout.visibility= View.GONE
            toolbarTitle.text = getString(R.string.set_pin)
            toolbarTitle.textSize = 20f
        }
        binding.pinLayout.setOnClickListener(null)
        val ic: InputConnection? = binding.walletPinEditText.onCreateInputConnection(EditorInfo())
        binding.keyboard.setInputConnection(ic)
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


        binding.walletPinEditText.onTextCompleteListener = object : OnTextCompleteListener {
            override fun onTextComplete(enteredText: String): Boolean {
                if (enteredText.length == 4) {
                    Handler().postDelayed({
                        submitPin()
                                          }, 50)
                }
                return true // Return true to keep the keyboard open else return false to close the keyboard
            }
        }
    }

    private fun submitPin() {
        val pin: String = binding.walletPinEditText.text.toString()
        if (pin.length == 4) {
            if(isFromSplash!!){
                val storedPin = Utils.retrievePinSecurely(this)
                if(storedPin==pin){
                    startActivity(Intent(this,HomeScreenActivity::class.java))
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
            else{
                callPinCheckApi(pin)
            }
        }
        else {
            ObjectAnimator
                .ofFloat(binding.walletPinEditText, "translationX", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
                .setDuration(500)
                .start()
            Utils.vibrate(this, 500)
            showPopUpMessage(getString(R.string.pin_must_be_of_four_digit))
            // pinEdTxt.setAnimation(shake);
        }
    }

    private fun callPinCheckApi(pin:String){
        binding.tvError.text = getString(R.string.re_enter_pin)
        if(isFirstAttempt){
           firstAttemptPin = pin
           binding.walletPinEditText.setText("")
           isFirstAttempt=false
       }
        else{
           if(firstAttemptPin==pin){
                Utils.showToast(this,"Pin set up has been completed !!")
                Utils.storePinSecurely(this,pin)
                val intent = Intent(this, AppPinActivity::class.java)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
           else{
               binding.walletPinEditText.setText("")
               Utils.vibrate(this,500)
               showPopUpMessage("Pin does not match? please re-enter again")
           }
        }
    }

    override fun onClick(view: View) {
        submitPin()
    }
}