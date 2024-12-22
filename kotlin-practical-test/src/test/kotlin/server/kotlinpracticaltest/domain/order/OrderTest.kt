package server.kotlinpracticaltest.domain.order

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.kotlinpracticaltest.domain.order.OrderStatus.INIT
import server.kotlinpracticaltest.domain.product.Product
import server.kotlinpracticaltest.domain.product.ProductType
import server.kotlinpracticaltest.domain.product.ProductType.HANDMADE
import server.kotlinpracticaltest.domain.product.SellingStatus.SELLING
import java.time.LocalDateTime

class OrderTest {
    @Test
    fun `calculateTotalPrice`() {
        val product1 = createProduct("001", HANDMADE, 1000L)
        val product2 = createProduct("002", HANDMADE, 2000L)
        val product3 = createProduct("003", HANDMADE, 3000L)

        val order = Order.create(listOf(product1, product2, product3), LocalDateTime.now())

        assertThat(order.totalPrice).isEqualTo(6000L)
    }

    @Test
    fun `when create order then order status is INIT`() {
        val product1 = createProduct("001", HANDMADE, 1000L)
        val product2 = createProduct("002", HANDMADE, 2000L)
        val product3 = createProduct("003", HANDMADE, 3000L)

        val order = Order.create(listOf(product1, product2, product3), LocalDateTime.now())

        assertThat(order.orderStatus).isEqualByComparingTo(INIT)
    }

    @Test
    fun `주문 생성시 주문 등록 시간을 기록한다`() {
        val product1 = createProduct("001", HANDMADE, 1000L)
        val product2 = createProduct("002", HANDMADE, 2000L)
        val product3 = createProduct("003", HANDMADE, 3000L)

        val order = Order.create(listOf(product1, product2, product3), LocalDateTime.now())

        assertThat(order.registeredDateTime).isNotNull
    }

    private fun createProduct(
        productNumber: String,
        productType: ProductType,
        price: Long,
    ): Product =
        Product(
            productNumber = productNumber,
            type = productType,
            sellingStatus = SELLING,
            name = "메뉴이름",
            price = price,
        )
}
