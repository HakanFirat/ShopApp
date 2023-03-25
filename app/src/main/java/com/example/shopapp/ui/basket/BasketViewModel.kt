package com.example.shopapp.ui.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.data.model.BasketModel
import com.example.shopapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    val readAllBasket: LiveData<List<BasketModel>> = productRepository.readAllBasket

    private val _totalAmount = MutableLiveData(0.0)
    private val _totalCount = MutableLiveData(0)
    val totalAmount: LiveData<Double> = _totalAmount
    val totalCount: LiveData<Int> = _totalCount

    fun deleteFromBasket(basketId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.deleteFromBasket(basketId)
        }
    }

    fun deleteAllBasket() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.deleteAllBasket()
        }
    }

    private fun updateBasket(product: BasketModel) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.updateBasket(product)
        }
    }

    fun increase(product: BasketModel) {
        _totalAmount.value = _totalAmount.value?.plus(product.productPrice)
        _totalCount.value = _totalCount.value?.plus(product.count)
        updateBasket(product)
    }

    fun decrease(product: BasketModel) {
        _totalAmount.value = _totalAmount.value?.minus(product.productPrice)
        _totalCount.value = _totalCount.value?.minus(product.count)
        updateBasket(product)
    }

    fun resetTotalAmount() {
        _totalAmount.value = 0.0
    }

    fun totalBasket() {
        viewModelScope.launch() {
            _totalAmount.value = productRepository.totalBasket()
            _totalCount.value = productRepository.totalCount()
        }
    }
}