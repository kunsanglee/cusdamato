package server.kotlinpracticaltest.domain.product

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import server.kotlinpracticaltest.domain.BaseEntity

@Entity
class Product(
    var productNumber: String,
    @Enumerated(EnumType.STRING)
    var type: ProductType,
    @Enumerated(EnumType.STRING)
    var sellingStatus: SellingStatus,
    var name: String,
    var price: Long
) : BaseEntity()
