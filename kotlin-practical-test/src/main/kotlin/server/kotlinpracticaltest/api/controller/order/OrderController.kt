package server.kotlinpracticaltest.api.controller.order

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import server.kotlinpracticaltest.api.controller.order.request.OrderCreateRequest
import server.kotlinpracticaltest.api.service.order.OrderService
import java.time.LocalDateTime

@RestController
class OrderController(
    private val orderService: OrderService,
) {
    @PostMapping("api/v1/orders/new")
    fun createOrder(
        @RequestBody request: OrderCreateRequest,
    ) {
        val registeredDateTime = LocalDateTime.now()
        val orderResponse = orderService.createOrder(request, registeredDateTime)
    }
}
