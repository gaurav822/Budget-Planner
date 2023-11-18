package com.gaurav.budgetplanner.features.reminder.presentation.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.budgetplanner.databinding.ItemEachReminderBinding
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder

class ReminderAdapter(): RecyclerView.Adapter<ReminderAdapter.MyViewHolder>() {

    private var allRecords = emptyList<Reminder>()
    var onItemClick : ((Reminder) -> Unit)? = null
    var onSwitchChange :((Int,Boolean,Reminder) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            ItemEachReminderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allRecords.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val eachItem = allRecords[position]
        holder.binding.tvReminderName.text = eachItem.name
        holder.binding.btnRemind.isChecked = eachItem.isActive
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(eachItem)
        }

        holder.binding.btnRemind.setOnCheckedChangeListener { _, isChecked ->
            onSwitchChange?.invoke(eachItem.id,isChecked,eachItem)
        }
    }

    class MyViewHolder(b: ItemEachReminderBinding): RecyclerView.ViewHolder(b.root){
        var binding: ItemEachReminderBinding = b
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(data:List<Reminder>){
        this.allRecords = data
        notifyDataSetChanged()
    }
}