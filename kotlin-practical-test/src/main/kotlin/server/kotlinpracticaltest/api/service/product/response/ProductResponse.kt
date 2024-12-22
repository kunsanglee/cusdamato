package server.kotlinpracticaltest.api.service.product.response

import server.kotlinpracticaltest.domain.product.Product
import server.kotlinpracticaltest.domain.product.ProductType
import server.kotlinpracticaltest.domain.product.SellingStatus

data class ProductResponse(
    var id: Long = 0,
    var productNumber: String,
    var type: ProductType,
    var sellingStatus: SellingStatus,
    var name: String,
    var price: Long
) {
    companion object {
        fun from(product: Product): ProductResponse =
            ProductResponse(
                id = product.id,
                productNumber = product.productNumber,
                type = product.type,
                sellingStatus = product.sellingStatus,
                name = product.name,
                price = product.price
            )
    }
}
