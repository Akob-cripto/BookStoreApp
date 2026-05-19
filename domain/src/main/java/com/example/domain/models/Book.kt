package com.example.domain.models
data class Book(
    val id: String,
    val category: String,
    val imageUri: String,
    val title: String,
    val description: String,
    val author: String,
    val isFavorite: Boolean = false
)