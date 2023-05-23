package com.gaurav.budgetplanner.features.converter.Activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.gaurav.budgetplanner.features.converter.Fragments.CountrySelectFragment
import com.gaurav.budgetplanner.features.converter.model.Country
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityCountryListBinding

class CountryListActivity : BaseActivity() {
    private var _binding: ActivityCountryListBinding?= null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarCountry.mainGenericToolbar)
        binding.toolbarCountry.toolbarTitle.text =  "Currencies"

        init()
    }

    private fun init(){
        val countrySelectFragment = CountrySelectFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.countriesFrameLayout, countrySelectFragment)
        fragmentTransaction.commit()

        countrySelectFragment?.setListener(object : CountrySelectFragment.CountryClickListener{
            override fun onCountryClick(country: Country) {
//                val model: CountryViewModel = ViewModelProvider(this)[CountryViewModel::class.java]
//                model.select(country)
//                finish()
            }

        })

    }
}