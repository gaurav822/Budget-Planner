package com.gaurav.budgetplanner.features.reminder.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gaurav.budgetplanner.R
import java.util.Date

@Entity(tableName = "reminderTable")
data class Reminder(
    @ColumnInfo("name")val name:String,
    @ColumnInfo("date")val date:Long,
    @ColumnInfo("hour") val hour:Int,
    @ColumnInfo("minute") val minute:Int,
    @ColumnInfo("comment") val comment:String,
    @ColumnInfo("isActive") var isActive:Boolean=true,
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
):java.io.Serializable

class InValidReminderException(message:String) :Exception (message){

}