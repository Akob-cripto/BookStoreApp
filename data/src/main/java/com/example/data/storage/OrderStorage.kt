package com.example.data.storage

import com.example.data.storage.models.DataOrder

interface OrderStorage {
    suspend fun createOrder(order: DataOrder): Boolean
}