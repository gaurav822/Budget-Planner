package com.gaurav.budgetplanner.features.expensetracker.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gaurav.budgetplanner.R

@Entity(tableName = "recordTable")
data class Account(
    @ColumnInfo("amount")val amount:String,
    @ColumnInfo("category")val category:String,
    @ColumnInfo("transactionDate") val date:String,
    @ColumnInfo("comment") val comment:String,
    @ColumnInfo("transactionType")val transactionType:String,
    @ColumnInfo("timeStamp") val timeStamp:Long,
    @PrimaryKey (autoGenerate = true)
    var id:Int =0
):java.io.Serializable{
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