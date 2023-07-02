package com.gaurav.budgetplanner.Views.Components

import com.gaurav.budgetplanner.R

class IconMapper {

    companion object {
        fun getIconByName(iconCode:String):Int{
            return when(iconCode){

                "Transportation" -> return R.drawable.img_transport

                "Workout" -> R.drawable.img_gym

                "Family" -> R.drawable.img_family

                "Groceries" -> R.drawable.img_groceries

                "Gifts"-> R.drawable.img_gifts

                "Interest" -> R.drawable.img_interest

                "Gift" -> R.drawable.img_gifts

                "Paycheck" -> R.drawable.img_paycheck

                "Other" -> R.drawable.img_other

                else -> return R.drawable.ic_tab_indicator_default
            }
        }
    }
}