package server.kotlinpracticaltest.api.service.order

import org.springframework.stereotype.Service
import server.kotlinpracticaltest.api.controller.order.request.OrderCreateRequest
import server.kotlinpracticaltest.api.domain.order.OrderRepository
import server.kotlinpracticaltest.api.service.order.response.OrderResponse
import server.kotlinpracticaltest.domain.order.Order
import server.kotlinpracticaltest.domain.product.ProductRepository
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
) {
    fun createOrder(
        request: OrderCreateRequest,
        registeredDateTime: LocalDateTime,
    ): OrderResponse {
        val productNumbers = request.productNumbers
        val products = productRepository.findAllByProductNumberIn(productNumbers)
        val createdOrder = Order.create(products, registeredDateTime)

        orderRepository.save(createdOrder)

        return OrderResponse.from(createdOrder)
    }
}
