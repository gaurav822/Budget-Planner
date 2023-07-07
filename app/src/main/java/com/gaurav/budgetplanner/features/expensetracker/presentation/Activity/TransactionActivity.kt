package com.gaurav.budgetplanner.features.expensetracker.presentation.Activity

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.WindowManager
import android.widget.EditText
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityTransactionBinding
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters.CategoryAdapter
import com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel.RecordViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransactionActivity : BaseActivity() {
    private var _binding:ActivityTransactionBinding?=null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter

    private lateinit var recyclerView:RecyclerView
    private var isValid = false
    private var isCategorySelected = false
    private var isAmountValid = false
    private var isCommentValid = false
    private var trxType = "E"
//    private var viewModel:TransactionViewModel = hiltViewModel()
private lateinit var viewModel: RecordViewModel
private lateinit var selectedCategory:Map.Entry<String,Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setSupportActionBar(binding.constraintToolbar.toolbar)
        setMaterialTextWatcher(binding.inputAmount,R.id.input_amount)
        setMaterialTextWatcher(binding.inputComment.editText!!,R.id.inputComment)
        init()
        setRecyclerView()
        clickEventListener()


    }

    private fun init(){
        viewModel = ViewModelProvider(this)[RecordViewModel::class.java]
    }

    private fun setRecyclerView(){
        recyclerView = binding.rvCategories
        categoryAdapter = CategoryAdapter(Constants.categories,this)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3,1)
        recyclerView.adapter = categoryAdapter
        categoryAdapter.onItemClick = {
            selectedCategory = it
            isCategorySelected = true
            checkForValidation()
        }
//        recyclerView.addItemDecoration(AdaptiveSpacingItemDecoration(8,true));
//        bindAdapter(recyclerView,8.0f,true)

    }

    private fun clickEventListener(){
        binding.proceedStart.setOnClickListener {
            if(isValid){
                val account = Account(
                    amount = binding.inputAmount.editableText.toString().trim(),
                    category = selectedCategory.key,
                    comment = binding.inputComment.editText?.text.toString(),
                    timeStamp = System.currentTimeMillis(),
                    transactionType = trxType)
                viewModel.addRecord(account)
                finish()
            }
        }

        binding.editinputComment.onFocusChangeListener =
            OnFocusChangeListener { _, hasFocus ->
                if(hasFocus){
                    val scrollY = (binding.inputComment.y - 100).toInt()
                    binding.scrollView.smoothScrollTo(0, scrollY)
                }
            }


        binding.tabLayoutIncomeExpenses.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                 when(tab?.position){
                    0 -> {
                        trxType = "E"
                        categoryAdapter.updateData(Constants.categories.entries.toList())
                    }

                    1 -> {
                        trxType = "I"
                        categoryAdapter.updateData(Constants.incomeCategories.entries.toList())
                    }
                }
                isCategorySelected=false
                checkForValidation()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }


//    @BindingAdapter(value = ["spacingSize", "spacingEdgeEnabled"], requireAll = true)
//    fun bindAdapter(view: RecyclerView, spacingSize: Float, spacingEdgeEnabled: Boolean) {
//        with(view) {
//            addItemDecoration(
//                AdaptiveSpacingItemDecoration(spacingSize.toInt(), spacingEdgeEnabled)
//            )
//        }
//    }

    private fun setMaterialTextWatcher(editText: EditText,id:Int) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                when (id) {
                    R.id.input_amount -> {
                        isValidAmount()
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                //
            }
        })
    }

    private fun isValidAmount() {
        isAmountValid = binding.inputAmount.text.toString().isNotEmpty()
        checkForValidation()
    }

    private fun checkForValidation (){
        isValid = isAmountValid && isCategorySelected
        if(isValid) {
            binding.proceedStart.setBackgroundResource(R.drawable.boundry_proceed_button)
            binding.addText.setTextColor(Color.parseColor("#c6000000"))
        }
        else{
            binding.proceedStart.setBackgroundResource(R.drawable.boundry_proceed_disabled)
            binding.addText.setTextColor(Color.parseColor("#796727"))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }
}