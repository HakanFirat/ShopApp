package com.example.shopapp.data.repository

import androidx.lifecycle.LiveData
import com.example.shopapp.data.db.BasketDao
import com.example.shopapp.data.model.BasketModel
import com.example.shopapp.data.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val basketDao: BasketDao,
) {

    suspend fun getRealTimeProductList(): MutableList<ProductModel> = withContext(Dispatchers.IO) {
        val firebaseDatabase = FirebaseDatabase.getInstance().reference
        val productList = mutableListOf<ProductModel>()
        val getData = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children){
                    productList.add(
                        ProductModel(
                            id = i.child("id").value.toString(),
                            productTitle = i.child("title").value.toString(),
                            productDescription = i.child("description").value.toString(),
                            productPrice = i.child("price").value.toString(),
                            image = i.child("image").value.toString(),
                            detailImgList = i.child("imageList").value as ArrayList<String>,
                            productFeatures = i.child("product_features").value as Map<String, String>,
                        )
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        firebaseDatabase.addValueEventListener(getData)
        productList
    }

    suspend fun addToBasket(product: BasketModel) = basketDao.addToBasket(product)

    val readAllBasket: LiveData<List<BasketModel>> = basketDao.readAllBasket()

    suspend fun deleteFromBasket(basketId: String) = basketDao.deleteFromBasket(basketId)

    suspend fun deleteAllBasket() = basketDao.deleteAllBasket()

    suspend fun updateBasket(product: BasketModel) = basketDao.updateBasket(product)

    suspend fun totalBasket(): Double = basketDao.getTotalPrice()

    suspend fun totalCount(): Int = basketDao.getTotalCount()
}