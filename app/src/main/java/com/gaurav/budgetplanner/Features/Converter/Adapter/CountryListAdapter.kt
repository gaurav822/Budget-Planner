package com.gaurav.budgetplanner.Features.Converter.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.budgetplanner.Features.Converter.Model.Country
import com.gaurav.budgetplanner.databinding.ItemEachCountryBinding

class CountryListAdapter:RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {
    var onItemClick : ((Country) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemEachCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(Country("test","test"))
        }
    }

    class ViewHolder(b: ItemEachCountryBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemEachCountryBinding = b
    }
}