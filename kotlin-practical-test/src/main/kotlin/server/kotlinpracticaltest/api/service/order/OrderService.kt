package server.kotlinpracticaltest.api.service.order

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import server.kotlinpracticaltest.api.controller.order.request.OrderCreateRequest
import server.kotlinpracticaltest.api.service.order.response.OrderResponse
import server.kotlinpracticaltest.domain.order.Order
import server.kotlinpracticaltest.domain.order.OrderRepository
import server.kotlinpracticaltest.domain.product.Product
import server.kotlinpracticaltest.domain.product.ProductRepository
import server.kotlinpracticaltest.domain.product.ProductType
import server.kotlinpracticaltest.domain.stock.StockRepository
import java.time.LocalDateTime

@Transactional
@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val stockRepository: StockRepository,
) {
    fun createOrder(
        request: OrderCreateRequest,
        registeredDateTime: LocalDateTime,
    ): OrderResponse {
        val productNumbers = request.productNumbers
        val duplicateProducts = findProductsBy(productNumbers)

        deductStockQuantities(duplicateProducts)

        val createdOrder = Order.create(duplicateProducts, registeredDateTime)
        orderRepository.save(createdOrder)

        return OrderResponse.from(createdOrder)
    }

    private fun deductStockQuantities(duplicateProducts: List<Product>) {
        val stockProductNumbers =
            duplicateProducts
                .filter { ProductType.containsType(it.type) }
                .map { it.productNumber }

        val orderProductQuantity = stockProductNumbers.groupingBy { it }.eachCount()
        orderProductQuantity.forEach { (productNumber, orderQuantity) ->
            val stock =
                stockRepository.findByProductNumber(productNumber)
                    ?: throw IllegalArgumentException("존재하지 않는 상품번호 입니다. 상품번호: $productNumber")
            if (stock.isQuantityLessThan(orderQuantity)) {
                throw throw IllegalStateException(
                    "주문 수량이 재고보다 많습니다. 상품 번호: $productNumber, 재고: ${stock.quantity}, 요청 주문 수량: $orderQuantity",
                )
            }
            stock.deductQuantity(orderQuantity)
        }
    }

    private fun findProductsBy(productNumbers: List<String>): List<Product> {
        val products = productRepository.findAllByProductNumberIn(productNumbers)
        val productMap = products.associateBy { it.productNumber }
        return productNumbers.map { productMap[it] ?: throw IllegalArgumentException("Product not found: $it") }
    }
}
