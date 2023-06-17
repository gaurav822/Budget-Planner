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
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.binding.currencyName.text =  allItem[position].key.uppercase()
//        holder.binding.currencyCode.text = allItem[position].value.capitalize()
//        holder.itemView.setOnClickListener {
//            onItemClick?.invoke(allItem[position])
//            selectedItemPosition = holder.adapterPosition
//            notifyDataSetChanged()
//        }

//        if(selectedItemPosition==position){
//            holder.binding.parentContainer.setBackgroundColor(Color.parseColor("#29371d"))
//        }
//        else{
//            holder.binding.parentContainer.setBackgroundColor(Color.parseColor("#1a1e03"))
//        }
    }


    class ViewHolder(b: ItemTransactionCategoryBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemTransactionCategoryBinding = b
    }
}