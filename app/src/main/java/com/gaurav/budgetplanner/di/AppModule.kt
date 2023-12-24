package com.gaurav.budgetplanner.di

import android.app.Application
import androidx.room.Room
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.features.converter.data.data_source.CurrencyConvertApi
import com.gaurav.budgetplanner.features.converter.data.repository.ConversionRepoImpl
import com.gaurav.budgetplanner.features.converter.domain.repository.ConversionRepository
import com.gaurav.budgetplanner.features.converter.domain.use_case.ConvertUseCase
import com.gaurav.budgetplanner.features.expensetracker.data.data_source.TransactionDatabase
import com.gaurav.budgetplanner.features.expensetracker.data.repository.TransactionRepositoryImpl
import com.gaurav.budgetplanner.features.expensetracker.domain.repository.TransactionRepository
import com.gaurav.budgetplanner.features.expensetracker.domain.use_case.*
import com.gaurav.budgetplanner.features.reminder.data.repository.ReminderRepositoryImpl
import com.gaurav.budgetplanner.features.reminder.domain.repository.ReminderRepository
import com.gaurav.budgetplanner.features.reminder.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider
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


    @Provides
    @Singleton
    fun providesReminderRepo(db:TransactionDatabase):ReminderRepository{
        return ReminderRepositoryImpl(db.reminderDao)
    }



    @Provides
    @Singleton
    fun provideReminderUseCases(repository: ReminderRepository) : ReminderUseCases {
        return ReminderUseCases(
            getReminders = GetReminders(repository),
            deleteReminder = DeleteReminder(repository),
            addReminder = AddReminder((repository)),
            getReminder = GetReminder(repository),
            updateReminder = UpdateReminder(repository),
            updateIsActive = GetUpdateIsActive(repository)
        )
    }

    @Provides
    @Singleton
    fun provideConversionApi():CurrencyConvertApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyConvertApi::class.java)

    }

    @Provides
    @Singleton
    fun provideConversionRepository(api:CurrencyConvertApi): ConversionRepository{
        return ConversionRepoImpl(api)
    }


    @Provides
    @Singleton
    fun provideConversionUseCase(repository:ConversionRepository):ConvertUseCase{
        return ConvertUseCase(repository)
    }


}