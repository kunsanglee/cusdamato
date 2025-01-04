package server.kotlinpracticaltest.domain.stock

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import kotlin.test.Test

class StockTest {
    @Test
    fun `재고의 수량이 제공된 수량보다 적은지 확인한다`() {
        val stock = Stock.create("1234", 1)

        assertThat(stock.isQuantityLessThan(2)).isTrue
    }

    @Test
    fun `재고를 주어진 개수만큼 차감할 수 있다`() {
        val stock = Stock.create("1234", 1)

        assertThatCode { stock.deductQuantity(1) }.doesNotThrowAnyException()
        assertThat(stock.quantity).isZero
    }
}
