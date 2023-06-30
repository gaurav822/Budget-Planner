package com.gaurav.budgetplanner.features.expensetracker.data.data_source

import androidx.room.*
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query(value = "SELECT * FROM account")
    fun getTransaction() : Flow<List<Account>>

    @Query("SELECT * FROM account WHERE id = :id")
    suspend fun getTrxById(id:Int):Account?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrx(transaction: Account)

    @Delete
    suspend fun deleteTrx(transaction: Account)

}