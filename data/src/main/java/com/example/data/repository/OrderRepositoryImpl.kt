package com.example.data.repository

import com.example.data.storage.OrderStorage
import com.example.data.storage.models.DataOrder
import com.example.domain.models.NewOrderParam
import com.example.domain.models.Order
import com.example.domain.repositories.OrderRepository
import kotlin.String

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

    override suspend fun getMyOrders(): List<Order> {
        return orderStorage.getMyOrders().map { dataOrder ->
            Order(
                id = dataOrder.id,
                userId = dataOrder.userId,
                userEmail = dataOrder.userEmail,
                bookIds = dataOrder.bookIds,
                totalPrice = dataOrder.totalPrice,
                status = dataOrder.status,
                createdAtMillis = dataOrder.createdAtMillis
            )
        }
    }

    override suspend fun cancelOrder(orderId: String): Boolean {
        return orderStorage.cancelOrder(orderId)
    }
}