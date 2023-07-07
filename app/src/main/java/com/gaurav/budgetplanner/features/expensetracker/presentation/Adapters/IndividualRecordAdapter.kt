package com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gaurav.budgetplanner.Views.Components.IconMapper
import com.gaurav.budgetplanner.databinding.ItemIndividualRecordBinding
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account

class IndividualRecordAdapter(var category:String):RecyclerView.Adapter<IndividualRecordAdapter.ViewHolder>() {
    private var allRecords = emptyList<Account>()
    var onItemClick : ((Account) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            ItemIndividualRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eachData = allRecords[position]
        holder.binding.tvAmount.text = "NRs"+eachData.amount
        holder.binding.tvCategory.text = category
        Glide.with(holder.binding.tvAmount.context)
            .asBitmap()
            .load(IconMapper.getIconByName(category))
            .into(holder.binding.ivCategory)

        holder.binding.tvComment.text = eachData.comment
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(eachData)
        }
    }

    override fun getItemCount(): Int {
        return allRecords.size
    }

    class ViewHolder(b:ItemIndividualRecordBinding): RecyclerView.ViewHolder(b.root){
        var binding: ItemIndividualRecordBinding = b
    }

    fun updateList(data:List<Account>){
        this.allRecords = data
        notifyDataSetChanged()
    }
}