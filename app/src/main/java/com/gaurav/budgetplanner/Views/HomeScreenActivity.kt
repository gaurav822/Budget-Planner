package com.gaurav.budgetplanner.Views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityHomeScreenBinding
import com.gaurav.budgetplanner.features.expensetracker.presentation.Activity.TransactionActivity
import com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters.RecordAdapter
import com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeScreenActivity : BaseActivity() {
    private var _binding:ActivityHomeScreenBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:RecordViewModel
    private lateinit var adapter: RecordAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RecordViewModel::class.java]
        setUpRecyclerView()
        clickEvents()
    }

    private fun setUpRecyclerView(){
        adapter = RecordAdapter()
        binding.rvRecords.layoutManager = LinearLayoutManager(this)
        binding.rvRecords.adapter = adapter

        viewModel.allRecords.observe(this) { list ->
            list?.let {
                adapter.updateList(it)
            }
        }
    }

    private fun clickEvents(){
        binding.addIcon.setOnClickListener {
            startActivity(Intent(this, TransactionActivity::class.java))
        }
    }
}