package com.gaurav.budgetplanner.features.settings.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.databinding.ActivityAppPinBinding
import com.gaurav.budgetplanner.features.converter.domain.model.CurrencyModel

class AppPinActivity : AppCompatActivity() {
    private var _binding:ActivityAppPinBinding?=null
    private val binding get() = _binding!!
    private lateinit var intent: Intent
    private var isPinSetUpComplete:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAppPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        clickEvents()
    }

    private fun init(){
        intent = Intent(this,PinSetActivity::class.java)
        setSupportActionBar(binding.toolbar.mainGenericToolbar)
        binding.toolbar.apply {
            toolbarIconLayout.visibility= View.GONE
            toolbarTitle.text = getString(R.string.PIN)
            toolbarTitle.textSize = 20f
        }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent: Intent? = result.data
            intent?.extras.let {
                isPinSetUpComplete = true
                binding.apply {
                    tvYouCan.text = "Now you have to use PIN to login to app"
                    tvSetPin.text = "Change PIN"
                    tvRemovePin.visibility=View.VISIBLE
                }
            }
        }
    }

    private fun clickEvents(){
        binding.tvSetPin.setOnClickListener {
            intent.putExtra("isPinSetCompleted",isPinSetUpComplete)
            resultLauncher.launch(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}