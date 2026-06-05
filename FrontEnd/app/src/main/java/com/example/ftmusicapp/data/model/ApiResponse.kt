package com.example.ftmusicapp.data.model

data class ApiResponse<T>(
    val exitoso: Boolean,
    val mensaje: String,
    val data: T?
)