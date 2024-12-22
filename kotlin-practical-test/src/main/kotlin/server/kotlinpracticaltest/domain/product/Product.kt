package server.kotlinpracticaltest.domain.product

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import server.kotlinpracticaltest.domain.BaseEntity

@Entity
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var productNumber: String,
    @Enumerated(EnumType.STRING)
    var type: ProductType,
    @Enumerated(EnumType.STRING)
    var sellingStatus: SellingStatus,
    var name: String,
    var price: Long
) : BaseEntity()
