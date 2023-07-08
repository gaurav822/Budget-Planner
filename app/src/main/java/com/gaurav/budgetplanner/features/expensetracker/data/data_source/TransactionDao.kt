package com.gaurav.budgetplanner.features.expensetracker.data.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query(value = "SELECT * FROM recordTable order by id ASC")
    fun getTransaction() : LiveData<List<Account>>

    @Query("SELECT * FROM recordTable WHERE id = :id")
    suspend fun getTrxById(id:Int):Account?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrx(transaction: Account)

    @Update
    suspend fun updateTrx(transaction: Account)

    @Delete
    suspend fun deleteTrx(transaction: Account)

}