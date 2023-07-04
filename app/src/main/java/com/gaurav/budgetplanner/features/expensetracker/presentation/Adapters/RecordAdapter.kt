package com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.budgetplanner.Views.Components.IconMapper
import com.gaurav.budgetplanner.databinding.ItemEachRecordBinding
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.util.TransactionType

class RecordAdapter: RecyclerView.Adapter<RecordAdapter.MyViewHolder>() {

    private var allRecords = emptyList<Account>()
    private var sum:Int = 0

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
        holder.binding.tvAmount.text = "NRS ${eachItem.amount}"
        holder.binding.imgCategory.setImageResource(IconMapper.getIconByName(eachItem.category))
        holder.binding.tvPercent.text = "${(eachItem.amount.toInt() * 100 / sum)}%"

        if(allRecords.isEmpty()){

        }
    }

    class MyViewHolder(b:ItemEachRecordBinding): RecyclerView.ViewHolder(b.root){
        var binding: ItemEachRecordBinding = b
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(data:List<Account>, trxType:String){
        sum = 0
        val newData:List<Account> = if(trxType == "E"){
            data.filter { transaction ->  transaction.transactionType=="E"
            }
        } else{
            data.filter { transaction ->  transaction.transactionType=="I"
            }
        }
       val mergedList = newData.groupBy {
            it.category
        }.map {
               (category, accounts) ->
           val totalAmount = accounts.sumOf { it.amount.toInt() }
           Account(totalAmount.toString(), category, "", "", 0)
        }
        this.allRecords = mergedList
        for(i in mergedList){
            sum+= i.amount.toInt()
        }

        notifyDataSetChanged()
    }
}