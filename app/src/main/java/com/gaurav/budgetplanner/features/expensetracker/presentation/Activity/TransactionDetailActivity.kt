package com.gaurav.budgetplanner.features.expensetracker.presentation.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.gaurav.budgetplanner.databinding.ActivityTransactionDetailBinding
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionDetailActivity : AppCompatActivity() {
    private lateinit var _binding:ActivityTransactionDetailBinding
    private val binding get() = _binding!!
    private lateinit var viewModel: RecordViewModel
    private var bundle:Bundle? = null
    private var account: Account? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTransactionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        clickEvents()
    }

    private fun clickEvents(){
        binding.deleteButton.setOnClickListener {
            viewModel.deleteRecord(account!!)
            finish()
        }
    }

    private fun init(){
        bundle = intent.extras
        bundle.let {
            account = bundle?.getSerializable("account") as Account
        }
        viewModel = ViewModelProvider(this)[RecordViewModel::class.java]
    }
}