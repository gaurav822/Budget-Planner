package com.gaurav.budgetplanner.features.converter.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.databinding.ItemEachCountryBinding
import com.gaurav.budgetplanner.features.converter.ViewModel.CountryViewModel
import com.gaurav.budgetplanner.features.converter.model.Country
import java.util.*


class CountryListAdapter():RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {

    var onItemClick : ((Map.Entry<String,String>) -> Unit)? = null
    private var selectedItemPosition:Int = -1
    private val currencies = Constants.currencies.entries.toList()
    private var allItem:List<Map.Entry<String,String>> = Constants.currencies.entries.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemEachCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allItem.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.currencyName.text =  allItem[position].key.uppercase()
        holder.binding.currencyCode.text = allItem[position].value.capitalize()
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(allItem[position])
            selectedItemPosition = holder.adapterPosition
            notifyDataSetChanged()
        }

        if(selectedItemPosition==position){
            holder.binding.parentContainer.setBackgroundColor(Color.parseColor("#29371d"))
        }
        else{
            holder.binding.parentContainer.setBackgroundColor(Color.parseColor("#1a1e03"))
        }
    }

    fun getFilter(): Filter {
        selectedItemPosition=-1
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    allItem = currencies
                } else {
                    val filteredList :MutableList<Map.Entry<String,String>> = mutableListOf()
                    for (i in currencies) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (i.value.lowercase()
                                .contains(charString.lowercase(Locale.getDefault())) || i.key.lowercase()
                                .contains(
                                    charString.lowercase(
                                        Locale.getDefault()
                                    )
                                )
                        ) {
                            filteredList.add(i)
//                            Log.d("TEST-ADD", row.getValue())
                        }
                    }
                    allItem = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = allItem
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                allItem = filterResults.values as List<Map.Entry<String,String>>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(b: ItemEachCountryBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemEachCountryBinding = b
    }
}