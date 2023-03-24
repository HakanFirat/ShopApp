package com.example.shopapp.ui.ext

import android.widget.ImageView
import com.example.shopapp.R
import com.squareup.picasso.Picasso

fun ImageView.load(url: String?) {
    if (url == null || url.isEmpty())
        return
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_launcher_background)
        // .placeholder(R.drawable.progress_animation)
        .error(R.drawable.ic_launcher_background)
        .resize(640, 0)
        // .onlyScaleDown()
        .into(this)
}