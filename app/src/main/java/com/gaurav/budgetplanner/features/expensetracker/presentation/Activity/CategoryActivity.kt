package com.gaurav.budgetplanner.features.expensetracker.presentation.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.databinding.ActivityCategoryBinding
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters.IndividualRecordAdapter
import com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    private var _binding: ActivityCategoryBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter: IndividualRecordAdapter
    private lateinit var viewModel:RecordViewModel
    private lateinit var category: String
    private lateinit var bundle:Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.constraintToolbar.toolbar)
        binding.constraintToolbar.clGenericBar.visibility= View.GONE
        binding.constraintToolbar.clCategoryBar.visibility=View.VISIBLE
        init()
        setUpRecyclerView()
        viewModel = ViewModelProvider(this)[RecordViewModel::class.java]

        viewModel.getAllRecords().observe(this) { list ->
            list?.let { it ->
                val data = getRespectiveData(it)
                adapter.updateList(data)
                binding.constraintToolbar.totalAmount.text = "NRs"+list.sumOf {
                    it.amount.toInt()
                }.toString()
            }
            if(list.isEmpty()) finish()
        }


    }

    private fun init(){
        category = intent?.extras?.getString("category")!!
        binding.constraintToolbar.apply {
            tvCategory.text = category
        }
    }

    private fun getRespectiveData(data:List<Account>):List<Account>{
        return data.filter { transaction ->  transaction.category==category }
    }

    private fun setUpRecyclerView(){
        adapter = IndividualRecordAdapter(category)
        binding.rvIndividualData.layoutManager = LinearLayoutManager(this)
        binding.rvIndividualData.adapter = adapter
        adapter.onItemClick = {
            bundle = Bundle()
            bundle.putSerializable("account",it)
            val intent = Intent(this,TransactionDetailActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }
}