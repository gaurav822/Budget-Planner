package com.gaurav.budgetplanner.di

import android.app.Application
import androidx.room.Room
import com.gaurav.budgetplanner.features.expensetracker.data.data_source.TransactionDatabase
import com.gaurav.budgetplanner.features.expensetracker.data.repository.TransactionRepositoryImpl
import com.gaurav.budgetplanner.features.expensetracker.domain.repository.TransactionRepository
import com.gaurav.budgetplanner.features.expensetracker.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTrxDatabase(app: Application): TransactionDatabase {
        return Room.databaseBuilder(
            app,
            TransactionDatabase::class.java,
            TransactionDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesTrxRepository(db:TransactionDatabase):TransactionRepository{
        return TransactionRepositoryImpl(db.transactionDao)
    }


    @Provides
    @Singleton
    fun provideTrxUseCases(repository: TransactionRepository) : TransactionUseCases {
        return TransactionUseCases(
            getTransactions = GetTransactions(repository),
            deleteTransaction = DeleteTransaction(repository),
            addTransaction = AddTransaction((repository)),
            getTransaction = GetTransaction(repository),
            updateTransaction = UpdateTransaction(repository)
        )
    }
}