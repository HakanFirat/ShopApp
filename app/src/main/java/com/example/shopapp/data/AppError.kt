package com.example.shopapp.data

data class AppError(
    var code: Int = 0,
    val message: String = "",
    val type: ErrorType = ErrorType.UNDEFINED
)