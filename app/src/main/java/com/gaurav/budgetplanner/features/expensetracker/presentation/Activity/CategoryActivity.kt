package com.gaurav.budgetplanner.features.expensetracker.presentation.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gaurav.budgetplanner.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private var _binding: ActivityCategoryBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.constraintToolbar.toolbar)
        binding.constraintToolbar.clGenericBar.visibility= View.GONE
        binding.constraintToolbar.clCategoryBar.visibility=View.VISIBLE
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }
}