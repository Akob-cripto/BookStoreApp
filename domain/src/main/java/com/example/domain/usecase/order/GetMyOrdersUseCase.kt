package com.example.domain.usecase.order

import com.example.domain.models.Order
import com.example.domain.repositories.OrderRepository

class GetMyOrdersUseCase(
    private val orderRepository: OrderRepository
) {
    suspend fun execute(): List<Order> {
        return orderRepository.getMyOrders()
    }
}