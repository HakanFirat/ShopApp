package com.example.shopapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shopapp.data.model.BasketModel

@Dao
interface BasketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToBasket(basket: BasketModel)

    @Query("SELECT * FROM basket_table ORDER BY basket_id ASC")
    fun readAllBasket(): LiveData<List<BasketModel>>

    @Query("DELETE FROM basket_table WHERE uuid = :idInput")
    suspend fun deleteFromBasket(idInput: String)

    @Query("DELETE FROM basket_table")
    suspend fun deleteAllBasket()

    @Query("SELECT SUM(count * basket_price) FROM basket_table")
    suspend fun getTotalPrice(): Double

    @Query("SELECT SUM(count) FROM basket_table")
    suspend fun getTotalCount(): Int

    @Update
    suspend fun updateBasket(basket: BasketModel) // For count in adapter
}