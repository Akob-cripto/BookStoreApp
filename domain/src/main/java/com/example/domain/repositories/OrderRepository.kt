package com.example.domain.repositories

import com.example.domain.models.NewOrderParam

interface OrderRepository {
    suspend fun createOrder(order: NewOrderParam): Boolean
}