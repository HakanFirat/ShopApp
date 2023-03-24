package com.example.shopapp.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shopapp.data.Constants
import com.example.shopapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideBasketDao(appDatabase: AppDatabase) = appDatabase.basketDao()

    @Provides
    fun provideAppDatabase(appDatabaseBuilder: RoomDatabase.Builder<AppDatabase>) =
        appDatabaseBuilder.fallbackToDestructiveMigration().build()

    @Provides
    fun provideAppDatabaseBuilder(@ApplicationContext context: Context?) =
        Room.databaseBuilder(
            context!!,
            AppDatabase::class.java,
            Constants.Database.NAME)
}