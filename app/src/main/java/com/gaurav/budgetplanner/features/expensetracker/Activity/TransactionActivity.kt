package com.gaurav.budgetplanner.features.expensetracker.Activity

import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityTransactionBinding
import com.gaurav.budgetplanner.features.expensetracker.Adapters.CategoryAdapter


class TransactionActivity : BaseActivity() {
    private var _binding:ActivityTransactionBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setSupportActionBar(binding.constraintToolbar.toolbar)
        setRecyclerView()

    }

    private fun setRecyclerView(){
        val adapter = CategoryAdapter()
        binding.rvCategories.layoutManager = GridLayoutManager(this,3)
        binding.rvCategories.isNestedScrollingEnabled=false
        binding.rvCategories.adapter = adapter

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }
}