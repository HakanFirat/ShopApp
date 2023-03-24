package com.example.shopapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shopapp.data.model.BasketModel

@Database(
    entities = [
        BasketModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun basketDao(): BasketDao
}