package com.example.domain.usecase.order

import com.example.domain.models.NewOrderParam
import com.example.domain.repositories.OrderRepository

class CancelOrderUseCase(private val orderRepository: OrderRepository) {
    suspend fun execute(orderId: String): Boolean {
        return orderRepository.cancelOrder(orderId = orderId)
    }
}