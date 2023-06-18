package com.gaurav.budgetplanner.features.expensetracker.Adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.databinding.ItemTransactionCategoryBinding
import com.gaurav.budgetplanner.features.converter.ViewModel.CountryViewModel
import java.util.*

class CategoryAdapter():RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var onItemClick : ((Map.Entry<String,String>) -> Unit)? = null
    private var selectedItemPosition:Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemTransactionCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    class ViewHolder(b: ItemTransactionCategoryBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemTransactionCategoryBinding = b
    }
}