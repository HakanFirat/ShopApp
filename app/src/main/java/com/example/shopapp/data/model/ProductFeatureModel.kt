package com.example.shopapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductFeatureModel(
    val type: String = "",
    val feature: String = "",
): Parcelable