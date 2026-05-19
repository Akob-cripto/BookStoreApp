package com.example.data.storage.models

data class DataBook(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val description: String = "",
    val category: String = "",
    val imageUri: String = "",
    val isFavorite: Boolean = false
)