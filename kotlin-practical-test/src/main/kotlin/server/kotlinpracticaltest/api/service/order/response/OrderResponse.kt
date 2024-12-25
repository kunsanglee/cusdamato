package server.kotlinpracticaltest.api.service.order.response

import server.kotlinpracticaltest.api.service.product.response.ProductResponse
import server.kotlinpracticaltest.domain.order.Order
import java.time.LocalDateTime

data class OrderResponse(
    val id: Long,
    val totalPrice: Int,
    val registeredDateTime: LocalDateTime,
    val products: List<ProductResponse>
) {
    companion object {
        fun from(order: Order): OrderResponse =
            OrderResponse(
                id = order.id,
                totalPrice = order.totalPrice,
                registeredDateTime = order.registeredDateTime,
                products = order.orderProducts.map { ProductResponse.from(it.product) }
            )
    }
}
