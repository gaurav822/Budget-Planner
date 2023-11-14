package com.gaurav.budgetplanner.features.reminder.data.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder

@Dao
interface ReminderDao {

    @Query(value = "SELECT * FROM reminderTable order by id ASC")
    fun getReminders() : LiveData<List<Reminder>>

    @Query("SELECT * FROM reminderTable WHERE id = :id")
    suspend fun getReminderById(id:Int): Reminder?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun delete(reminder: Reminder)

}