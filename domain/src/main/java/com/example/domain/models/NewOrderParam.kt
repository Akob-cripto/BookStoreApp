package com.example.domain.models

data class NewOrderParam(
    val userEmail: String,
    val bookIds: List<String>,
    val totalPrice: Double
)