package com.gaurav.budgetplanner.features.expensetracker.Activity

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityTransactionBinding
import com.gaurav.budgetplanner.features.expensetracker.Adapters.AdaptiveSpacingItemDecoration
import com.gaurav.budgetplanner.features.expensetracker.Adapters.CategoryAdapter


class TransactionActivity : BaseActivity() {
    private var _binding:ActivityTransactionBinding?=null
    private val binding get() = _binding!!

    private lateinit var recyclerView:RecyclerView

    private var categories:Map<String,Int> =
        mapOf("Transportation" to R.drawable.img_transport,
        "Workout" to R.drawable.img_gym,
            "Family" to R.drawable.img_family,
            "Groceries" to R.drawable.img_groceries,
            "Gifts" to R.drawable.img_gifts,
            "More" to R.drawable.img_add)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setSupportActionBar(binding.constraintToolbar.toolbar)
        setRecyclerView()

    }

    private fun setRecyclerView(){
        recyclerView = binding.rvCategories
        val adapter = CategoryAdapter(categories)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3,1)
        recyclerView.isNestedScrollingEnabled=false
        recyclerView.adapter = adapter
//        recyclerView.addItemDecoration(AdaptiveSpacingItemDecoration(8,true));
//        bindAdapter(recyclerView,8.0f,true)

    }


//    @BindingAdapter(value = ["spacingSize", "spacingEdgeEnabled"], requireAll = true)
//    fun bindAdapter(view: RecyclerView, spacingSize: Float, spacingEdgeEnabled: Boolean) {
//        with(view) {
//            addItemDecoration(
//                AdaptiveSpacingItemDecoration(spacingSize.toInt(), spacingEdgeEnabled)
//            )
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }
}