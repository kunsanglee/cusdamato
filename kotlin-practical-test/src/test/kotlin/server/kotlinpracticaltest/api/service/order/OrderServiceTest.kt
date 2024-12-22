package server.kotlinpracticaltest.api.service.order

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import server.kotlinpracticaltest.api.controller.order.request.OrderCreateRequest
import server.kotlinpracticaltest.domain.product.Product
import server.kotlinpracticaltest.domain.product.ProductRepository
import server.kotlinpracticaltest.domain.product.ProductType
import server.kotlinpracticaltest.domain.product.ProductType.HANDMADE
import server.kotlinpracticaltest.domain.product.SellingStatus.SELLING
import java.time.LocalDateTime
import kotlin.test.Test

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest(
    @Autowired val orderService: OrderService,
    @Autowired val productRepository: ProductRepository,
) {
    @Test
    fun `createOrder`() {
        val product1 = createProduct("001", HANDMADE, 4000L)
        val product2 = createProduct("002", HANDMADE, 4500L)
        val product3 = createProduct("003", HANDMADE, 7000L)
        productRepository.saveAll(listOf(product1, product2, product3))

        val request = OrderCreateRequest(listOf("001", "002"))
        val registeredDateTime = LocalDateTime.now()

        val orderResponse = orderService.createOrder(request, registeredDateTime)

        with(orderResponse) {
            assertThat(this.id).isNotNull
            assertThat(this)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 8500)
            assertThat(this.products)
                .hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                    tuple("001", 4000L),
                    tuple("002", 4500L),
                )
        }
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
