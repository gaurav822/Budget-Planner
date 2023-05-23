package com.gaurav.budgetplanner.features.converter.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.features.converter.model.Country
import com.gaurav.budgetplanner.features.converter.ViewModel.CountryViewModel
import com.gaurav.budgetplanner.databinding.ItemEachCountryBinding


class CountryListAdapter(private val countryViewModel: CountryViewModel):RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {

    var onItemClick : ((Country) -> Unit)? = null
    private val currencies = Constants.currencies.entries.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemEachCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.currencyName.text =  currencies[position].key.uppercase()
        holder.binding.currencyCode.text = currencies[position].value.capitalize()
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(Country("test","test"))
            countryViewModel.select(Country("test","test"))
        }
    }

    class ViewHolder(b: ItemEachCountryBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemEachCountryBinding = b
    }
}