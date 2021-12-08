package com.pandorina.legendaryquotes.di

import android.content.Context
import androidx.room.Room
import com.pandorina.legendaryquotes.data.QuoteDao
import com.pandorina.legendaryquotes.data.QuoteDatabase
import com.pandorina.legendaryquotes.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DATABASE_NAME = "quote_table.db"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        QuoteDatabase::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(database: QuoteDatabase) = database.dao()

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) = context

    @Provides
    @Singleton
    fun provideRepository(dao: QuoteDao, context: Context) = Repository(dao, context)
}