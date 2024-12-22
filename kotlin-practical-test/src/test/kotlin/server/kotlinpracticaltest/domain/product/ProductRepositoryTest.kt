package server.kotlinpracticaltest.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import server.kotlinpracticaltest.domain.product.ProductType.HANDMADE
import server.kotlinpracticaltest.domain.product.SellingStatus.HOLD
import server.kotlinpracticaltest.domain.product.SellingStatus.SELLING
import server.kotlinpracticaltest.domain.product.SellingStatus.STOP_SELLING

@ActiveProfiles("test")
@SpringBootTest
class ProductRepositoryTest(
    @Autowired private val productRepository: ProductRepository,
) {
    @Test
    fun `findAllBySellingStatusIn`() {
        val product1 =
            Product(
                productNumber = "001",
                type = HANDMADE,
                sellingStatus = SELLING,
                name = "아메리카노",
                price = 4000L,
            )
        val product2 =
            Product(
                productNumber = "002",
                type = HANDMADE,
                sellingStatus = HOLD,
                name = "카페라떼",
                price = 4500L,
            )
        val product3 =
            Product(
                productNumber = "003",
                type = HANDMADE,
                sellingStatus = STOP_SELLING,
                name = "팥빙수",
                price = 7000L,
            )

        productRepository.saveAll(listOf(product1, product2, product3))

        val products = productRepository.findAllBySellingStatusIn(SellingStatus.forDisplay())

        assertThat(products)
            .hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", SELLING),
                tuple("002", "카페라떼", HOLD),
            )
    }

    @Test
    fun `findAllByProductNumberIn`() {
        val product1 =
            Product(
                productNumber = "001",
                type = HANDMADE,
                sellingStatus = SELLING,
                name = "아메리카노",
                price = 4000L,
            )
        val product2 =
            Product(
                productNumber = "002",
                type = HANDMADE,
                sellingStatus = HOLD,
                name = "카페라떼",
                price = 4500L,
            )
        val product3 =
            Product(
                productNumber = "003",
                type = HANDMADE,
                sellingStatus = STOP_SELLING,
                name = "팥빙수",
                price = 7000L,
            )

        productRepository.saveAll(listOf(product1, product2, product3))

        val products = productRepository.findAllByProductNumberIn(listOf("001", "003"))

        assertThat(products)
            .hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", SELLING),
                tuple("003", "팥빙수", STOP_SELLING),
            )
    }
}
