package com.example.shopapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val id: String = "",
    val image: String = "",
    val detailImgList: ArrayList<String> = arrayListOf(),
    val productTitle: String = "",
    val productDescription: String = "",
    val productPrice: String = "",
    val productCount: String = "",
    val productFeatures: ArrayList<ProductFeatureModel> = arrayListOf(),
    var totalAmount: Double = 0.0
) : Parcelable