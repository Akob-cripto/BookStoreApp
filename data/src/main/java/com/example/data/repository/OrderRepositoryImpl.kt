package com.example.data.repository

import com.example.data.storage.OrderStorage
import com.example.data.storage.models.DataOrder
import com.example.domain.models.NewOrderParam
import com.example.domain.repositories.OrderRepository

class OrderRepositoryImpl(
    private val orderStorage: OrderStorage
) : OrderRepository {

    override suspend fun createOrder(order: NewOrderParam): Boolean {
        return orderStorage.createOrder(
            DataOrder(
                userEmail = order.userEmail,
                bookIds = order.bookIds,
                totalPrice = order.totalPrice,
                status = "created"
            )
        )
    }
}