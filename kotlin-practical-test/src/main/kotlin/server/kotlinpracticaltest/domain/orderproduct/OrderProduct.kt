package server.kotlinpracticaltest.domain.orderproduct

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import server.kotlinpracticaltest.domain.BaseEntity
import server.kotlinpracticaltest.domain.order.Order
import server.kotlinpracticaltest.domain.product.Product

@Entity
class OrderProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    var order: Order,
    @ManyToOne(fetch = FetchType.LAZY)
    var product: Product,
) : BaseEntity()
