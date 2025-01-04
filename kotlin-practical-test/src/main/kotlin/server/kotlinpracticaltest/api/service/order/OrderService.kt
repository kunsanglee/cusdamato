package server.kotlinpracticaltest.api.service.order

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import server.kotlinpracticaltest.api.controller.order.request.OrderCreateRequest
import server.kotlinpracticaltest.api.service.order.response.OrderResponse
import server.kotlinpracticaltest.domain.order.Order
import server.kotlinpracticaltest.domain.order.OrderRepository
import server.kotlinpracticaltest.domain.product.Product
import server.kotlinpracticaltest.domain.product.ProductRepository
import server.kotlinpracticaltest.domain.stock.StockRepository
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val stockRepository: StockRepository,
) {
    @Transactional
    fun createOrder(request: OrderCreateRequest, registeredDateTime: LocalDateTime): OrderResponse {
        val productNumbers = request.productNumbers
        val duplicateProducts = findProductsBy(productNumbers)
        val orderProductQuantity = duplicateProducts.groupingBy { it.productNumber }.eachCount()
        orderProductQuantity.forEach { (productNumber, orderQuantity) ->
            val stock = (
                stockRepository.findByProductNumber(productNumber)
                    ?: throw IllegalArgumentException("존재하지 않는 상품번호 입니다. 상품번호: $productNumber")
                )
            stock.decreaseQuantity(orderQuantity)
        }
        val createdOrder = Order.create(duplicateProducts, registeredDateTime)
        orderRepository.save(createdOrder)

        return OrderResponse.from(createdOrder)
    }

    private fun findProductsBy(productNumbers: List<String>): List<Product> {
        val products = productRepository.findAllByProductNumberIn(productNumbers)
        val productMap = products.associateBy { it.productNumber }
        return productNumbers.map { productMap[it] ?: throw IllegalArgumentException("Product not found: $it") }
    }
}
