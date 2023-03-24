package com.example.shopapp.ui.ext

import android.view.View
import androidx.core.content.ContextCompat
import com.example.shopapp.R
import com.google.android.material.snackbar.Snackbar

fun View.showErrorSnackBar(message: String, errorMessage: Boolean) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view

    if (errorMessage) {
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.color_snack_bar_error
            )
        )
    } else {
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.color_snack_bar_success
            )
        )
    }
    snackBar.show()
}