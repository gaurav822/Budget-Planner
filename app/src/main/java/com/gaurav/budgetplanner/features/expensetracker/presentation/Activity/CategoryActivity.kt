package com.gaurav.budgetplanner.features.expensetracker.presentation.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.budgetplanner.Utils.Utils
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
    private lateinit var currency: String
    private lateinit var bundle:Bundle
    private var searchEnabled:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.constraintToolbar.toolbar)
        binding.constraintToolbar.clGenericBar.visibility= View.GONE
        binding.constraintToolbar.clCategoryBar.visibility=View.VISIBLE
        init()
        setUpRecyclerView()
        setSearchFieldListener()
        viewModel = ViewModelProvider(this)[RecordViewModel::class.java]

        viewModel.getAllRecords().observe(this) { list ->
            list?.let { it ->
                val data = getRespectiveData(it)
                adapter.updateList(data)
                val sum = data.sumOf {
                    it.amount.toInt()
                }
                binding.constraintToolbar.totalAmount.text = sum.toString()
                binding.constraintToolbar.tvCurrencySymbol.text = currency
                if(data.isEmpty()) finish()
            }

        }

        binding.constraintToolbar.searchButton.setOnClickListener {
                binding.constraintToolbar.apply {
                    clCategoryBar.visibility=View.GONE
                    inputSearchField.visibility=View.VISIBLE
                    inputSearchField.requestFocus()
                    Utils.showKeyboard(inputSearchField)
                searchEnabled=true
            }
        }


    }

    private fun init(){
        category = intent?.extras?.getString("category")!!
        currency = intent?.extras?.getString("currency")!!

        binding.constraintToolbar.apply {
            tvCategory.text = category
        }
    }


    private fun setSearchFieldListener() {
        binding.constraintToolbar.inputSearchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    // buttonClose.setVisibility(View.GONE);
                    searchItem("")
                } else {
                    searchItem(s)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }


    private fun searchItem(query: CharSequence) {
        adapter.let {
            Handler().postDelayed({
               adapter.getFilter().filter(query)
                                              }, 80)
        }
    }


    private fun getRespectiveData(data:List<Account>):List<Account>{
        return data.filter { transaction ->  transaction.category==category }
    }

    private fun setUpRecyclerView(){
        adapter = IndividualRecordAdapter(category,currency)
        binding.rvIndividualData.layoutManager = LinearLayoutManager(this)
        binding.rvIndividualData.adapter = adapter
        adapter.onItemClick = {
            bundle = Bundle()
            bundle.putSerializable("account",it)
            val intent = Intent(this,TransactionDetailActivity::class.java)
            intent.putExtras(bundle)
            intent.putExtra("currency",currency)
            startActivity(intent)
        }

        adapter.onEmptyData = {
            if(it){
                binding.noDataTv.visibility=View.VISIBLE
                binding.rvIndividualData.visibility=View.GONE
            }
            else{
                binding.noDataTv.visibility=View.GONE
                binding.rvIndividualData.visibility=View.VISIBLE
            }

        }
    }


    override fun onSupportNavigateUp(): Boolean {
        if(searchEnabled){
            binding.constraintToolbar.apply {
                inputSearchField.setText("")
                inputSearchField.visibility=View.GONE
                clCategoryBar.visibility=View.VISIBLE
            }
            searchEnabled=false
            Utils.hideSoftKeyboard(this,binding.constraintToolbar.inputSearchField)
            return false
        }
        onBackPressed()
        return true;
    }
}