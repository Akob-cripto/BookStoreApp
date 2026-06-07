package com.example.domain.models

data class Order(
    val id: String,
    val userId: String,
    val userEmail: String,
    val bookIds: List<String>,
    val totalPrice: Double,
    val status: String,
    val createdAtMillis: Long
)