package com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.budgetplanner.databinding.ItemEachRecordBinding
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account

class RecordAdapter: RecyclerView.Adapter<RecordAdapter.MyViewHolder>() {

    private var allRecords = emptyList<Account>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            ItemEachRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return allRecords.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val eachItem = allRecords[position]
        holder.binding.categoryTitle.text = eachItem.category
        holder.binding.tvAmount.text = "NRS ${eachItem.amount}"
    }

    class MyViewHolder(b:ItemEachRecordBinding): RecyclerView.ViewHolder(b.root){
        var binding: ItemEachRecordBinding = b
    }

    fun updateList(data:List<Account>){
        this.allRecords = data
        notifyDataSetChanged()
    }
}