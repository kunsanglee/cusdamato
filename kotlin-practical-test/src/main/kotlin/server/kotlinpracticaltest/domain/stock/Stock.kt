package server.kotlinpracticaltest.domain.stock

import jakarta.persistence.Entity
import server.kotlinpracticaltest.domain.BaseEntity

@Entity
class Stock(
    var productNumber: String,
    var quantity: Int
) : BaseEntity() {
    fun isQuantityLessThan(orderQuantity: Int): Boolean = quantity < orderQuantity

    fun deductQuantity(orderQuantity: Int) {
        if (isQuantityLessThan(orderQuantity)) {
            throw throw IllegalStateException(
                "주문 수량이 재고보다 많습니다. 상품 번호: $productNumber, 재고: $quantity, 요청 주문 수량: $orderQuantity",
            )
        }
        this.quantity -= orderQuantity
    }

    companion object {
        fun create(
            productNumber: String,
            quantity: Int,
        ): Stock {
            return Stock(
                productNumber = productNumber,
                quantity = quantity,
            )
        }
    }
}
