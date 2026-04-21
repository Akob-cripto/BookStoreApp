package com.example.domain.models




data class NewBookParam(
    val id: String = "",
    val category: String,
    val imageUri: String,
    val title: String,
    val description: String)