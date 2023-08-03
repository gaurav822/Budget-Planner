package com.gaurav.budgetplanner.features.settings.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.databinding.ActivityAppPinBinding

class AppPinActivity : AppCompatActivity() {
    private var _binding:ActivityAppPinBinding?=null
    private val binding get() = _binding!!
    private lateinit var intent: Intent
    private var isPinActive:Boolean? = false
    private var pin:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAppPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        clickEvents()
    }

    private fun init(){
        intent = Intent(this, PinSetUpActivity::class.java)
        pin = Utils.retrievePinSecurely(this)
        pin?.let {
            isPinActive=true
            setHasPinLayout(isPinActive!!)
        }
        intent = Intent(this,PinSetUpActivity::class.java)
        setSupportActionBar(binding.toolbar.mainGenericToolbar)
        binding.toolbar.apply {
            toolbarIconLayout.visibility= View.GONE
            toolbarTitle.text = getString(R.string.PIN)
            toolbarTitle.textSize = 20f
        }
    }

    private fun setHasPinLayout(isActive:Boolean){
        if(isActive){
            binding.apply {
                tvYouCan.text = getString(R.string.now_you_have_to)
                tvSetPin.text = getString(R.string.change_pin)
                tvRemovePin.visibility=View.VISIBLE
            }
        }
        else{
            binding.apply {
                tvYouCan.text = getString(R.string.you_can_set_pin)
                tvSetPin.text = getString(R.string.set_pin)
                tvRemovePin.visibility=View.GONE
            }
        }

    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            pin = Utils.retrievePinSecurely(this)
            val intentForData: Intent? = result.data
            val data = intentForData?.getStringExtra("intentData")
            if(data=="delete"){
                isPinActive=false
                setHasPinLayout(isPinActive!!)
            }
            else {
                intent.putExtra("pin",pin)
                isPinActive=true
                setHasPinLayout(isPinActive!!)
            }
        }
    }

    private fun clickEvents(){
        binding.tvSetPin.setOnClickListener {
            intent.putExtra("pinRequestedFrom",if(isPinActive!!)"changePin" else "setPin")
            intent.putExtra("pin",pin)
            resultLauncher.launch(intent)
        }

        binding.tvRemovePin.setOnClickListener {
            intent.putExtra("pinRequestedFrom","deletePin")
            intent.putExtra("pin",pin)
            resultLauncher.launch(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}