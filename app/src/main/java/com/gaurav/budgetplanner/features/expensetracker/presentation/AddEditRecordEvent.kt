package com.gaurav.budgetplanner.features.expensetracker.presentation

sealed class AddEditRecordEvent{
    data class EnteredAmount(val value:String):AddEditRecordEvent()
    data class EnteredCategory(val value:String):AddEditRecordEvent()
    data class EnteredComment(val value:String):AddEditRecordEvent()
    object SaveRecord:AddEditRecordEvent()
}
