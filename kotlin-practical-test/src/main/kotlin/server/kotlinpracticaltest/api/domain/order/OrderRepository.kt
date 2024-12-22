package server.kotlinpracticaltest.api.domain.order

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import server.kotlinpracticaltest.domain.order.Order

@Repository
interface OrderRepository : JpaRepository<Order, Long>
