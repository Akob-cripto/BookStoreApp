package com.example.domain.repositories

import com.example.domain.models.NewOrderParam
import com.example.domain.models.Order

interface OrderRepository {
    suspend fun createOrder(order: NewOrderParam): Boolean
    suspend fun getMyOrders(): List<Order>

    suspend fun cancelOrder(orderId: String): Boolean
}