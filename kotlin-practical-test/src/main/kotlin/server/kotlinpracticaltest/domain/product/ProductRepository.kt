package server.kotlinpracticaltest.domain.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findAllBySellingStatusIn(sellingStatuses: Collection<SellingStatus>): List<Product>
    fun findAllByProductNumberIn(productNumbers: Collection<String>): List<Product>
}
