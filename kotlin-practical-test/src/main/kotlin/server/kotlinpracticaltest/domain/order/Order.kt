package server.kotlinpracticaltest.domain.order

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import server.kotlinpracticaltest.domain.BaseEntity
import server.kotlinpracticaltest.domain.orderproduct.OrderProduct
import server.kotlinpracticaltest.domain.product.Product
import java.time.LocalDateTime

@Table(name = "orders")
@Entity
class Order(
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus,
    var totalPrice: Int,
    var registeredDateTime: LocalDateTime,
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderProducts: MutableList<OrderProduct> = mutableListOf()
) : BaseEntity() {
    companion object {
        fun create(
            products: Collection<Product>,
            registeredDateTime: LocalDateTime
        ): Order {
            val order =
                Order(
                    orderStatus = OrderStatus.INIT,
                    registeredDateTime = registeredDateTime,
                    totalPrice = calculateTotalPrice(products)
                )
            order.orderProducts = createOrderProducts(products, order)

            return order
        }

        private fun calculateTotalPrice(products: Collection<Product>): Int = products.sumOf { it.price }.toInt()

        private fun createOrderProducts(
            products: Collection<Product>,
            order: Order
        ) = products
            .map {
                OrderProduct(
                    order = order,
                    product = it
                )
            }.toMutableList()
    }
}
