package com.example.domain.usecase.order

import com.example.domain.models.NewOrderParam
import com.example.domain.repositories.OrderRepository

class CreateOrderUseCase(
    private val orderRepository: OrderRepository
) {
    suspend fun execute(order: NewOrderParam): Boolean {
        return orderRepository.createOrder(order)
    }
}