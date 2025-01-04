package server.kotlinpracticaltest.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import server.kotlinpracticaltest.domain.product.ProductType.BAKERY
import server.kotlinpracticaltest.domain.product.ProductType.BOTTLE
import server.kotlinpracticaltest.domain.product.ProductType.Companion.containsType
import server.kotlinpracticaltest.domain.product.ProductType.HANDMADE
import kotlin.test.Test

class ProductTypeTest {

    @Test
    fun `재고 관리가 필요한 상품 타입(병음료, 빵)인지 반환한다`() {
        assertAll(
            { assertThat(containsType(BOTTLE)).isTrue },
            { assertThat(containsType(BAKERY)).isTrue },
            { assertThat(containsType(HANDMADE)).isFalse }
        )
    }
}
