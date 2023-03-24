package com.example.shopapp.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.shopapp.ui.ext.load

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
        imageUrl?.let {
            imageView.load(url = imageUrl)
        }
    }
}