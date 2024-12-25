package server.kotlinpracticaltest.domain.stock

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface StockRepository : JpaRepository<Stock, Long> {
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByProductNumber(productNumber: String): Stock?
}
