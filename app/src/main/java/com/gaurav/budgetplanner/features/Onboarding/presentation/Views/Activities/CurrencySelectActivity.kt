package com.gaurav.budgetplanner.features.Onboarding.presentation.Views.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.budgetplanner.BudgetPlannerApp
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.Views.HomeScreenActivity
import com.gaurav.budgetplanner.databinding.ActivityCurrencySelectBinding
import com.gaurav.budgetplanner.features.converter.Activities.CurrencyConvertActivity
import com.gaurav.budgetplanner.features.converter.Adapter.CountryListAdapter
import com.gaurav.budgetplanner.features.converter.model.CurrencyModel

class CurrencySelectActivity : AppCompatActivity() {
    private var _binding: ActivityCurrencySelectBinding?= null
    private val binding get() = _binding!!
    private var allItemAdapter:CountryListAdapter?=null
    private var isSelected = false
    private lateinit var selectedCurrency:String
    private lateinit var selectedCurrencyValue:String
    private var isFromConverter:Boolean?=null
    private lateinit var bundle:Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCurrencySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        setUpRecyclerView()
        setSearchFieldListener()
        binding.proceedStart.setOnClickListener {
            if(!isSelected){
                Toast.makeText(this,"Please choose 1 from above",Toast.LENGTH_SHORT).show()
            }
            else{
                BudgetPlannerApp.getStorage().edit().putBoolean(
                    Constants.PREF_CURRENCY_SELECTION,
               true).apply()

                BudgetPlannerApp.getStorage().edit().putString(
                    Constants.PREF_CURRENCY_SYMBOL,selectedCurrency).apply()

                BudgetPlannerApp.getStorage().edit().putString(
                    Constants.PREF_CURRENCY_VALUE,selectedCurrencyValue).apply()

                val intent = Intent(this,HomeScreenActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }
        }

    }

    private fun initData(){
       bundle = Bundle()
      isFromConverter=  intent.extras?.getBoolean("fromConverter")
      isFromConverter?.let {
          binding.proceedStart.visibility=View.GONE
          binding.chooseCurr.text = getString(R.string.select_currency)
      }
    }


    private fun setUpRecyclerView(){
        allItemAdapter = CountryListAdapter()
        binding.rvCountry.layoutManager = LinearLayoutManager(this)
        binding.rvCountry.adapter = allItemAdapter
        allItemAdapter?.onEachItemClick = {
            data ->
            isFromConverter?.let {
                val intent = Intent(this, CurrencyConvertActivity::class.java)
                bundle.putSerializable("currency",data)
                intent.putExtras(bundle)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            isSelected=true
            selectedCurrency = data.currencySymbol
            selectedCurrencyValue = data.currencyCode
        }
    }

    private fun setSearchFieldListener() {
        binding.etSearchCountry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    // buttonClose.setVisibility(View.GONE);
                    searchItem("")
                } else {
                    isSelected = false
                    searchItem(s)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun searchItem(query: CharSequence) {
        if (allItemAdapter != null) {
            Handler().postDelayed({ allItemAdapter?.getFilter()?.filter(query) }, 80)
        }
    }

    interface CountryClickListener {
        fun onCountryClick(country: CurrencyModel)
    }

//    fun setListener(listener: CountryClickListener) {
//        this.listener = listener
//    }
}