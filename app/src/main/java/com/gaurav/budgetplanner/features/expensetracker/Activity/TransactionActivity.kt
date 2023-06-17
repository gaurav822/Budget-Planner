package com.gaurav.budgetplanner.features.expensetracker.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
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
        setSupportActionBar(binding.toolbar)

        setReyclerView()

    }

    private fun setReyclerView(){
        val adapter = CategoryAdapter()
        binding.rvCategories.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.rvCategories.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }
}