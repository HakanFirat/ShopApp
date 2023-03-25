package com.example.shopapp.ui.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.data.model.BasketModel
import com.example.shopapp.data.model.ProductFeatureModel
import com.example.shopapp.data.model.ProductModel
import com.example.shopapp.data.repository.ProductRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository

): ViewModel() {

    val productListLiveData = MutableLiveData<List<ProductModel>>()

    fun getProductList() {
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
                            detailImgList = i.child("imageList").value as MutableList<String>,
                            productFeatures = i.child("product_features").value as Map<String, String>,
                        )
                    )
                }
                viewModelScope.launch {
                    productListLiveData.postValue(productList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        firebaseDatabase.addValueEventListener(getData)
    }

    fun addToBasket(basket: BasketModel) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.addToBasket(basket)
        }
    }
}