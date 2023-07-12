package com.gaurav.budgetplanner.features.converter.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.Views.Components.IconMapper
import com.gaurav.budgetplanner.databinding.ItemEachCountryBinding
import com.gaurav.budgetplanner.features.converter.ViewModel.CountryViewModel
import com.gaurav.budgetplanner.features.converter.model.Country
import org.json.JSONObject
import java.util.*


class CountryListAdapter():RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {

    var onItemClick : ((Map.Entry<String,Map<String,String>>) -> Unit)? = null
    private var selectedItemPosition:Int = -1
    private val currencies = Constants.currencies.entries.toList()
    private var allItem= Constants.currencies.entries.toList()

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
        holder.binding.currencyName.text =  allItem[position].value["name"]
        holder.binding.currencyCode.text = allItem[position].key
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(allItem[position])
            selectedItemPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
        Glide.with(holder.itemView.context)
            .asBitmap()
            .load("https://flagsapi.com/${allItem[position].value["isoAlpha2"]}/flat/64.png")
            .into(holder.binding.countryLogo)

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
                    val filteredList :MutableList<Map.Entry<String,Map<String,String>>> = mutableListOf()
                    for (i in currencies) {
                        if (i.value["name"]?.lowercase()!!
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
                allItem = filterResults.values as List<Map.Entry<String,Map<String,String>>>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(b: ItemEachCountryBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemEachCountryBinding = b
    }
}