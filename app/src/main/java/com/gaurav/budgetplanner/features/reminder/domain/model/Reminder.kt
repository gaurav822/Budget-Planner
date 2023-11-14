package com.gaurav.budgetplanner.features.reminder.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gaurav.budgetplanner.R
import java.util.Date

@Entity(tableName = "reminderTable")
data class Reminder(
    @ColumnInfo("name")val name:String,
    @ColumnInfo("date")val date:String,
    @ColumnInfo("time") val time:Long,
    @ColumnInfo("comment") val comment:String,
    @ColumnInfo("isActive") val isActive:Boolean,
    @PrimaryKey(autoGenerate = true)
    var id:Int =0
):java.io.Serializable

class InValidReminderException(message:String) :Exception (message){

}