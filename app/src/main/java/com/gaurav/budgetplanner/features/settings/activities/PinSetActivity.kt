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
    private var pinNotVerified = true
    private var firstAttemptPin = ""
    private var pinRequestedFrom:String? = ""
    private var pin:String? = null
    private var changePin:Boolean=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = PinSetActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init(){
        pinRequestedFrom = intent.extras?.getString("pinRequestedFrom")
        pin = intent.extras?.getString("pin")
        setPinLayoutData(pinRequestedFrom)
        binding.toolbarPin.apply {
            toolbarIconLayout.visibility= View.GONE
            toolbarTitle.textSize = 20f
        }
        binding.pinLayout.setOnClickListener(null)
        val ic: InputConnection? = binding.walletPinEditText.onCreateInputConnection(EditorInfo())
        binding.keyboard.setInputConnection(ic)
        setListeners()
    }

    private fun setPinLayoutData(pinRequestedFrom:String?){
        pinRequestedFrom?.let {
             when(pinRequestedFrom){
                 "splash" -> {
                     binding.toolbarPin.mainGenericToolbar.visibility=View.GONE
                     binding.tvError.text =  getString(R.string.please_enter_pin_continue)
                 }
                 "changePin" -> {
                     binding.toolbarPin.toolbarTitle.text =getString(R.string.change_pin)
                     binding.tvError.text = getString(R.string.you_must_enter_curr)
                 }
                 "setPin" -> {
                     binding.toolbarPin.toolbarTitle.text =getString(R.string.set_pin)
                     binding.tvError.text =getString(R.string.choose_a_pin)
                 }
                 "deletePin" -> {
                     binding.toolbarPin.toolbarTitle.text =getString(R.string.remove_pin)
                     binding.tvError.text = getString(R.string.you_must_enter_curr)
                 }
            }
        }
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
        val currentPin: String = binding.walletPinEditText.text.toString()

        pin?.let {
            if(!pinRequestedFrom.equals("changePin") && currentPin!=it){
                binding.walletPinEditText.setText("")
                Utils.showToast(this,"Invalid PIN, Please try again")
                return
            }
            else{
                if(pinRequestedFrom.equals("splash")){
                    startActivity(Intent(this,HomeScreenActivity::class.java))
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                else if(pinRequestedFrom.equals("deletePin")){
                    deletePinFromSystem()
                }

                else if(pinRequestedFrom.equals("changePin")){
                    if(currentPin!=pin && pinNotVerified){
                        binding.walletPinEditText.setText("")
                        Utils.showToast(this,"Invalid PIN, Please try again")
                        return
                    }
                    else{
                        binding.walletPinEditText.setText("")
                        binding.tvError.text = getString(R.string.choose_a_new_pin)
                        pinNotVerified=false
                        callPinCheckApi(currentPin,changePin)
                    }
                }
            }

        }?:kotlin.run {
            callPinCheckApi(currentPin,false)
        }
//        else {
//            ObjectAnimator
//                .ofFloat(binding.walletPinEditText, "translationX", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
//                .setDuration(500)
//                .start()
//            Utils.vibrate(this, 500)
//            showPopUpMessage(getString(R.string.pin_must_be_of_four_digit))
//            // pinEdTxt.setAnimation(shake);
//        }
    }

    private fun deletePinFromSystem(){
        Utils.storePinSecurely(this,null)
        val intent = Intent(this, AppPinActivity::class.java)
        intent.putExtra("intentData","delete")
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun callPinCheckApi(pin:String,changePin:Boolean){
            if(changePin) {
                this.changePin = false
                return
            }
             binding.tvError.text = getString(R.string.re_enter_pin)
             if(isFirstAttempt){
                 firstAttemptPin = pin
                 binding.walletPinEditText.setText("")
                 isFirstAttempt = false
             }
             else {
                 if (firstAttemptPin == pin) {
                     Utils.showToast(this, "Pin set up has been completed !!")
                     Utils.storePinSecurely(this, pin)
                     val intent = Intent(this, AppPinActivity::class.java)
                     setResult(Activity.RESULT_OK, intent)
                     finish()
                 } else {
                     binding.walletPinEditText.setText("")
                     Utils.vibrate(this, 500)
                     showPopUpMessage("Pin does not match? please re-enter again")
                 }
             }

    }

    override fun onClick(view: View) {
        submitPin()
    }
}