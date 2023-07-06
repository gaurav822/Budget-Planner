package com.gaurav.budgetplanner.Views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityHomeScreenBinding
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.util.TransactionType
import com.gaurav.budgetplanner.features.expensetracker.presentation.Activity.CategoryActivity
import com.gaurav.budgetplanner.features.expensetracker.presentation.Activity.TransactionActivity
import com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters.RecordAdapter
import com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel.RecordViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeScreenActivity : BaseActivity() {
    private var _binding:ActivityHomeScreenBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:RecordViewModel
    private lateinit var adapter: RecordAdapter
    private var currentList:List<Account> = emptyList()
    private var trxState = "E"
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

        adapter.onItemClick = {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        viewModel.getAllRecords().observe(this) { list ->
            list?.let {
                val data = splitAndMergedData(it)
                adapter.updateList(data)
                currentList = it
            }
        }
    }

    private fun clickEvents(){
        binding.addIcon.setOnClickListener {
            startActivity(Intent(this, TransactionActivity::class.java))
        }

        binding.tabLayoutIncomeExpenses.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        trxState = "E"
                    }
                    1 -> {
                        trxState = "I"
                    }

                }
                adapter.updateList(splitAndMergedData(currentList))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun splitAndMergedData(data:List<Account>):List<Account>{
        val newData: List<Account>
        if(trxState == "E"){
                newData =  data.filter { transaction ->  transaction.transactionType=="E" }
                checkIfDataEmpty(newData)
        } else{
               newData = data.filter { transaction ->  transaction.transactionType=="I" }
               checkIfDataEmpty(newData)
        }
        val mergedList = newData.groupBy {
            it.category
        }.map {
                (category, accounts) ->
            val totalAmount = accounts.sumOf { it.amount.toInt() }
            Account(totalAmount.toString(), category, "", "", 0)
        }
        return mergedList
    }

    private fun checkIfDataEmpty(data:List<Account>){
        if(data.isEmpty()){
            binding.noDataTv.visibility=View.VISIBLE
            if(trxState=="E"){
                binding.noDataTv.text = "No Expenses Found !"
            }
            else{
                binding.noDataTv.text = "No Income Found !"
            }
        }
        else{
            binding.noDataTv.visibility=View.GONE
        }
    }
}