package server.kotlinpracticaltest.api.controller.product

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import server.kotlinpracticaltest.api.service.product.ProductService
import server.kotlinpracticaltest.api.service.product.response.ProductResponse

@RestController
class ProductController(
    private val productService: ProductService
) {
    @GetMapping("/api/v1/products/selling")
    fun getSellingProducts(): List<ProductResponse> = productService.getSellingProducts()
}
