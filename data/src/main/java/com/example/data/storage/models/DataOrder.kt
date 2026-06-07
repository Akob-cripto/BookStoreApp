package com.example.data.storage.models

data class DataOrder(
    val id: String = "",
    val userId: String = "",
    val userEmail: String = "",
    val bookIds: List<String> = emptyList(),
    val totalPrice: Double = 0.0,
    val status: String = "created",
    val createdAtMillis: Long = 0L
)