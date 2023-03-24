package com.example.shopapp.ui.common

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.shopapp.R
import com.example.shopapp.data.AppError
import javax.inject.Inject

@Suppress("DEPRECATION")
class DialogUtils @Inject constructor() {

    private var progressDialog: ProgressDialog? = null

    fun showProgressDialog(context: Context?) {
        context?.let {
            ProgressDialog(context).apply {
                setCancelable(false)
                setMessage(context.getString(R.string.please_wait))
                isIndeterminate = true
                setProgressStyle(ProgressDialog.STYLE_SPINNER)
                show()
                progressDialog = this
            }
        }
    }

    fun hideProgressDialog() {
        progressDialog?.hide()
        progressDialog = null
    }

    fun showAlertDialog(context: Context?, error: AppError) {
        context?.let {
            val message = if (error.message.isNotEmpty()) {
                error.message
            } else {
                context.getString(R.string.generic_error)
            }
            AlertDialog.Builder(context)
                .setTitle(R.string.warning)
                .setMessage(message)
                .setPositiveButton(
                    context.getString(R.string.ok)
                ) { dialogInterface, i -> // set what would happen when positive button is clicked
                    dialogInterface.dismiss()
                }
                .show()
        }
    }
}