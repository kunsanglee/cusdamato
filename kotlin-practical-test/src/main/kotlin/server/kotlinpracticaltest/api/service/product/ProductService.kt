package server.kotlinpracticaltest.api.service.product

import org.springframework.stereotype.Service
import server.kotlinpracticaltest.api.service.product.response.ProductResponse
import server.kotlinpracticaltest.domain.product.ProductRepository
import server.kotlinpracticaltest.domain.product.SellingStatus

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun getSellingProducts(): List<ProductResponse> {
        val products = productRepository.findAllBySellingStatusIn(SellingStatus.forDisplay())
        return products.map { ProductResponse.from(it) }
    }
}
