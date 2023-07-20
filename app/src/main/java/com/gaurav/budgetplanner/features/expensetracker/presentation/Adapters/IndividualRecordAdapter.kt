package com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gaurav.budgetplanner.Views.Components.IconMapper
import com.gaurav.budgetplanner.databinding.ItemIndividualRecordBinding
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import java.util.*

class IndividualRecordAdapter(var category:String,var currency:String):RecyclerView.Adapter<IndividualRecordAdapter.ViewHolder>() {
    private var allRecords = emptyList<Account>()
    var onItemClick : ((Account) -> Unit)? = null
    var onEmptyData : ((Boolean) -> Unit)? = null
    private var mData:List<Account>?=null
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
        holder.binding.tvAmount.text = eachData.amount
        holder.binding.tvCurrency.text = currency
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


    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                allRecords = if (charString.isEmpty()) {
                    mData!!
                } else {
                    val filteredList :MutableList<Account> = mutableListOf()
                    for (i in mData!!) {
                        if (i.comment.lowercase()
                                .contains(charString.lowercase(Locale.getDefault()))
                        ) {
                            filteredList.add(i)
            //                            Log.d("TEST-ADD", row.getValue())
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = mData
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                mData = filterResults.values as List<Account>
                notifyDataSetChanged()
                onEmptyData?.invoke(allRecords.isEmpty())
            }
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
        this.mData=data
        notifyDataSetChanged()
    }
}