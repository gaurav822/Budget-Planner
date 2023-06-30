package com.gaurav.budgetplanner.features.expensetracker.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gaurav.budgetplanner.R

@Entity
data class Account(
    val amount:String,
    val category:String,

    val comment:String,
    val transactionType:String,
    val timeStamp:Long,
    @PrimaryKey val id:Int? =null
){
    companion object {
        val categories:Map<String,Int> =
            mapOf("Transportation" to R.drawable.img_transport,
                "Workout" to R.drawable.img_gym,
                "Family" to R.drawable.img_family,
                "Groceries" to R.drawable.img_groceries,
                "Gifts" to R.drawable.img_gifts,
                "More" to R.drawable.img_add,)
    }
}

class InValidTransactionException(message:String) :Exception (message){

}