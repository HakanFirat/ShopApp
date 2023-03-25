package com.example.shopapp.ui.product.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.data.model.BasketModel
import com.example.shopapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel@Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    fun addToBasket(basket: BasketModel) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.addToBasket(basket)
        }
    }

}