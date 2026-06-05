package com.example.domain.models

data class NewBookParam(
    val category: String,
    val imageUri: String,
    val title: String,
    val description: String,
    val author: String,
    val price: Double
)