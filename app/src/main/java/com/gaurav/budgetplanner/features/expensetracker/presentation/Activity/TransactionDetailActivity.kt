package com.gaurav.budgetplanner.features.expensetracker.presentation.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
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
    private var currency:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTransactionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar.mainGenericToolbar)
        init()
        clickEvents()
    }

    private fun clickEvents(){
        binding.deleteButton.setOnClickListener {
            viewModel.deleteRecord(account!!)
            finish()
        }

        binding.toolbar.toolbarGPSIcon.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("account",account)
            val intent = Intent(this,TransactionActivity::class.java)
            intent.putExtras(bundle)
            resultLauncher.launch(intent)
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent: Intent? = result.data
            intent?.extras.let {
                account = it?.getSerializable("account") as Account
                setData()
            }
        }
    }

    private fun init(){
        bundle = intent.extras
        currency = bundle?.getString("currency")
        bundle.let {
            account = bundle?.getSerializable("account") as Account
        }
        viewModel = ViewModelProvider(this)[RecordViewModel::class.java]
        setData()
    }

    private fun setData(){
        binding.amount.text = account?.amount?.toInt().toString()
        binding.tvCurrency.text = currency
        binding.category.text = account?.category
        binding.date.text = account?.date

        if(account?.comment?.isNotEmpty()!!){
            binding.apply {
                comment.visibility= View.VISIBLE
                comment.text = account?.comment
                tvComment.visibility=View.VISIBLE
            }
        }
        else{
            binding.comment.visibility=View.GONE
            binding.tvComment.visibility=View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }
}