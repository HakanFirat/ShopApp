package com.example.shopapp.domain.product

import com.example.shopapp.data.model.ProductModel
import com.example.shopapp.data.repository.ProductRepository
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val productRepository: ProductRepository

) {

    suspend operator fun invoke(): MutableList<ProductModel> {
        return productRepository.getRealTimeProductList()
    }
}