package com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.Views.Components.IconMapper
import com.gaurav.budgetplanner.databinding.ItemEachRecordBinding
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.util.TransactionType

class RecordAdapter: RecyclerView.Adapter<RecordAdapter.MyViewHolder>() {

    private var allRecords = emptyList<Account>()
    private var sum:Int = 0
    var onItemClick : ((Account) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            ItemEachRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return allRecords.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val eachItem = allRecords[position]
        holder.binding.categoryTitle.text = eachItem.category
        holder.binding.tvAmount.text = holder.itemView.context.getString(R.string.nrs,eachItem.amount.toInt())
        Glide.with(holder.binding.categoryTitle.context)
            .asBitmap()
            .load(IconMapper.getIconByName(eachItem.category))
            .into(holder.binding.imgCategory)
        holder.binding.tvPercent.text = Utils.calculatePercentage(allRecords.map {
            it.amount.toInt()
        }.toList(),position).toString() + "%"

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(eachItem)
        }
    }

    class MyViewHolder(b:ItemEachRecordBinding): RecyclerView.ViewHolder(b.root){
        var binding: ItemEachRecordBinding = b
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(data:List<Account>){
        sum = 0
        this.allRecords = data
        for(i in data){
            sum+= i.amount.toInt()
        }

        notifyDataSetChanged()
    }
}