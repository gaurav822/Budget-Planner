package com.gaurav.budgetplanner.features.expensetracker.Activity

import android.R.attr.button
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.OnFocusChangeListener
import android.view.WindowManager
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityTransactionBinding
import com.gaurav.budgetplanner.features.expensetracker.Adapters.CategoryAdapter
import com.google.android.material.tabs.TabLayout


class TransactionActivity : BaseActivity() {
    private var _binding:ActivityTransactionBinding?=null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter

    private lateinit var recyclerView:RecyclerView
    private var isValid = false
    private var isCategorySelected = false
    private var isAmountValid = false
    private var isCommentValid = false

    private var categories:Map<String,Int> =
        mapOf("Transportation" to R.drawable.img_transport,
        "Workout" to R.drawable.img_gym,
            "Family" to R.drawable.img_family,
            "Groceries" to R.drawable.img_groceries,
            "Gifts" to R.drawable.img_gifts,
            "More" to R.drawable.img_add,
            "Transportation" to R.drawable.img_transport,
            "Workout" to R.drawable.img_gym,
            "Family" to R.drawable.img_family,
            "Groceries" to R.drawable.img_groceries,
            "Gifts" to R.drawable.img_gifts,
            "More" to R.drawable.img_add)

    private var incomeCategories:Map<String,Int> =
        mapOf(
            "Interest" to R.drawable.img_transport,
            "Gift" to R.drawable.img_gym,
            "Paycheck" to R.drawable.img_family,
            "Other" to R.drawable.img_groceries,
            "More" to R.drawable.img_add,
            "Transportation" to R.drawable.img_transport,
            "Workout" to R.drawable.img_gym,
            "Family" to R.drawable.img_family,
            "Groceries" to R.drawable.img_groceries,
            "Gifts" to R.drawable.img_gifts,
            "More" to R.drawable.img_add
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setSupportActionBar(binding.constraintToolbar.toolbar)
        setMaterialTextWatcher(binding.inputAmount,R.id.input_amount)
        setMaterialTextWatcher(binding.inputComment.editText!!,R.id.inputComment)
        setRecyclerView()
        clickEventListener()


    }

    private fun setRecyclerView(){
        recyclerView = binding.rvCategories
        categoryAdapter = CategoryAdapter(categories)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3,1)
        recyclerView.isNestedScrollingEnabled=false
        recyclerView.adapter = categoryAdapter
        categoryAdapter.onItemClick = {
            isCategorySelected = true
            checkForValidation()
        }
//        recyclerView.addItemDecoration(AdaptiveSpacingItemDecoration(8,true));
//        bindAdapter(recyclerView,8.0f,true)

    }

    private fun clickEventListener(){
        binding.proceedStart.setOnClickListener {
            categoryAdapter.updateData(incomeCategories.entries.toList())
        }

        binding.tabLayoutIncomeExpenses.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                 when(tab?.position){
                    0 -> {
                        categoryAdapter.updateData(categories.entries.toList())
                    }

                    1 -> {
                        categoryAdapter.updateData(incomeCategories.entries.toList())
                    }
                }
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


                    R.id.inputComment -> {
                        isValidComment()
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
    private fun isValidComment() {
        isCommentValid = binding.inputComment.editText?.text.toString().isNotEmpty()
        checkForValidation()
    }

    private fun checkForValidation (){
        isValid = isAmountValid && isCategorySelected && isCommentValid
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