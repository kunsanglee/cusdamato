package server.kotlinpracticaltest.api.controller.order.request

data class OrderCreateRequest(
    val productNumbers: List<String>,
)
