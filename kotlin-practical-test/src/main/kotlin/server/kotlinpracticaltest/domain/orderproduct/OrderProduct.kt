package server.kotlinpracticaltest.domain.orderproduct

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import server.kotlinpracticaltest.domain.BaseEntity
import server.kotlinpracticaltest.domain.order.Order
import server.kotlinpracticaltest.domain.product.Product

@Entity
class OrderProduct(
    @ManyToOne(fetch = FetchType.LAZY)
    var order: Order,
    @ManyToOne(fetch = FetchType.LAZY)
    var product: Product
) : BaseEntity()
