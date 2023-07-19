package com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.databinding.ItemTransactionCategoryBinding
import com.gaurav.budgetplanner.features.converter.ViewModel.CountryViewModel
import java.util.*

class CategoryAdapter(categories:Map<String,Int>,var context:Context):RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var onItemClick : ((Map.Entry<String,Int>) -> Unit)? = null
    private var selectedItemPosition:Int = -1

    var data = categories.entries.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemTransactionCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eachData = data[position]
        holder.binding.tvTransaction.text = eachData.key
        Glide.with(context)
            .asBitmap()
            .load(eachData.value)
            .into(holder.binding.ivTransaction)

        holder.binding.ivTransaction.setBackgroundResource(getRespectiveCircle(position))

        holder.itemView.setOnClickListener {
                onItemClick?.invoke(data[position])
                selectedItemPosition = holder.adapterPosition
                notifyDataSetChanged()
        }

        if(selectedItemPosition==position){
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(context,
                getRespectiveBackColor(position)))
        }
        else{
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }
    }


    class ViewHolder(b: ItemTransactionCategoryBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemTransactionCategoryBinding = b
    }

    fun updateData(newCategories:List<Map.Entry<String,Int>>){
        this.data = newCategories
        selectedItemPosition = -1
        notifyDataSetChanged()
    }

    private fun getRespectiveCircle(index:Int):Int{
        return when(index){
            0 -> R.drawable.circle
            1 -> R.drawable.circle_green
            2 -> R.drawable.circle_red
            3 -> R.drawable.circle_light_blue
            4 -> R.drawable.circle_green
            else -> R.drawable.circle_grey
        }
    }

    private fun getRespectiveBackColor(index: Int):Int {
        return when(index){
            0-> R.color.darkBlueColor
            1 -> R.color.light_green
            2 -> R.color.rippleColor
            3 -> R.color.sky_blue
            4 -> R.color.light_green
            else -> R.color.lightGrey
        }
    }

    fun updateSelectedItem(category:String){
        for (i in data.indices){
            if(data[i].key==category){
                selectedItemPosition = i
                break
            }
        }
        notifyDataSetChanged()
    }
}