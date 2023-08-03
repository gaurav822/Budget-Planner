package com.gaurav.budgetplanner.features.expensetracker.presentation.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.Views.Components.IconMapper
import com.gaurav.budgetplanner.databinding.ActivityTransactionBinding
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters.CategoryAdapter
import com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel.RecordViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


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
    private lateinit var viewModel: RecordViewModel
    private lateinit var selectedCategory:Map.Entry<String,Int>

    private var account:Account?= null
    private var bundle:Bundle? = null
    private var editModeOn = false
    private var selectedDate: String? = null

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
        clickEventListener()

    }

    private fun init(){
        selectedDate = Utils.getFormattedDateFromMillis(System.currentTimeMillis(),null)
        viewModel = ViewModelProvider(this)[RecordViewModel::class.java]
        setRecyclerView()
        getIntentData()
        binding.inputDateOfTrx.editText?.setText(selectedDate)
        binding.currency.text = Utils.getSelectedCurrency(isSymbol = false)
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

    private fun getIntentData(){
        bundle = intent?.extras
        bundle?.let {
            account = bundle?.getSerializable("account") as Account
        }
        account?.let {
            triggerEditMode()
        }
    }

    private fun triggerEditMode(){
        editModeOn = true
        binding.apply {
            constraintToolbar.tvProfileName.text = getString(R.string.edit_trx)
            inputAmount.setText(account?.amount)
            inputComment.editText?.setText(account?.comment)
            addText.text = getString(R.string.save)
        }
        isCategorySelected = true
        isAmountValid = true
        selectedDate = account?.date
        checkForValidation()

        selectedCategory= mapOf(account?.category!! to IconMapper.getIconByName(account?.category!!)).entries.first()
        if(account?.transactionType=="I"){
            trxType = "I"
            binding.tabLayoutIncomeExpenses.getTabAt(1)?.select()
            categoryAdapter.updateData(Constants.incomeCategories.entries.toList())
        }
        categoryAdapter.updateSelectedItem(account?.category!!)
    }

    private fun clickEventListener(){
        binding.inputDateOfTrx.editText?.setOnClickListener {
            pickDate()
        }

        binding.proceedStart.setOnClickListener {
            if(isValid){
                val insertAcc = Account(
                    amount = binding.inputAmount.editableText.toString().trim(),
                    category = selectedCategory.key,
                    comment = binding.inputComment.editText?.text.toString(),
                    timeStamp = System.currentTimeMillis(),
                    transactionType = trxType,
                    date = selectedDate!!
                )
                if(editModeOn){
                    insertAcc.id = account?.id!!
                    viewModel.updateRecord(insertAcc)
                    val bundle = Bundle()
                    bundle.putSerializable("account",insertAcc)
                    val intent = Intent(this,TransactionDetailActivity::class.java)
                    intent.putExtras(bundle)
                    setResult(Activity.RESULT_OK, intent)
                }
                else viewModel.addRecord(insertAcc)
                finish()
            }
            else{
                if(!isAmountValid){
                   Toast.makeText(this,"Invalid Amount!",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"Please select category !",Toast.LENGTH_SHORT).show()
                }
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

    private var trackDate: Long = 0L


    private fun pickDate() {
        val materialDateBuilder = MaterialDatePicker.Builder.datePicker()
        if (trackDate == 0L) {
            trackDate = System.currentTimeMillis()
        }
        materialDateBuilder.setSelection(trackDate)
        materialDateBuilder.setTitleText(getText(R.string.trx_day))

// Setting max limit of dates to be picked
        val constraintsBuilder = CalendarConstraints.Builder()
        constraintsBuilder.setEnd(System.currentTimeMillis())

// Setting the constraints on the materialDateBuilder
        materialDateBuilder.setCalendarConstraints(constraintsBuilder.build())

        val materialDatePicker: MaterialDatePicker<Long> = materialDateBuilder.build()

        materialDatePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")
        materialDatePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            trackDate = calendar.timeInMillis
            selectedDate = Utils.getFormattedDateFromMillis(trackDate, null)
            binding.inputDateOfTrx.editText?.setText(selectedDate)
        }

    }

    private fun isValidAmount() {
        isAmountValid = binding.inputAmount.text.toString().isNotEmpty()  && binding.inputAmount.text.toString().length<9 && binding.inputAmount.text.toString().toInt()>0
        if(isAmountValid){
            binding.errorText.visibility= View.GONE
            binding.liner.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        else{
            binding.errorText.visibility= View.VISIBLE
            binding.liner.setBackgroundColor(Color.parseColor("#dd8b37"))
        }
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