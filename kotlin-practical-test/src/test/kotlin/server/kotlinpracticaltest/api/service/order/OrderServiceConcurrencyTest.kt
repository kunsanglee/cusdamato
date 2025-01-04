package server.kotlinpracticaltest.api.service.order

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import server.kotlinpracticaltest.api.controller.order.request.OrderCreateRequest
import server.kotlinpracticaltest.domain.order.OrderRepository
import server.kotlinpracticaltest.domain.orderproduct.OrderProductRepository
import server.kotlinpracticaltest.domain.product.Product
import server.kotlinpracticaltest.domain.product.ProductRepository
import server.kotlinpracticaltest.domain.product.ProductType
import server.kotlinpracticaltest.domain.product.ProductType.HANDMADE
import server.kotlinpracticaltest.domain.product.SellingStatus.SELLING
import server.kotlinpracticaltest.domain.stock.Stock
import server.kotlinpracticaltest.domain.stock.StockRepository
import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class OrderServiceConcurrencyTest(
    @Autowired private val orderService: OrderService,
    @Autowired private val orderProductRepository: OrderProductRepository,
    @Autowired private val productRepository: ProductRepository,
    @Autowired private val stockRepository: StockRepository,
    @Autowired private val orderRepository: OrderRepository,
) {
    @AfterEach
    fun tearDown() {
        orderProductRepository.deleteAllInBatch()
        productRepository.deleteAllInBatch()
        stockRepository.deleteAllInBatch()
        orderRepository.deleteAllInBatch()
    }

    @Test
    fun `동시성 주문 생성 테스트`() {
        val product1 = createProduct("001", HANDMADE, 4000L)
        val product2 = createProduct("002", HANDMADE, 4500L)
        val product3 = createProduct("003", HANDMADE, 7000L)
        productRepository.saveAll(listOf(product1, product2, product3))

        // 초기 재고 설정
        val productNumber = "001"
        val initialStock = 100
        stockRepository.save(Stock.create(productNumber, initialStock))

        val numberOfThreads = 10
        val ordersPerThread = 10
        val orderQuantity = 1
        val executor = Executors.newFixedThreadPool(numberOfThreads)
        val latch = CountDownLatch(numberOfThreads)

        repeat(numberOfThreads) {
            executor.submit {
                try {
                    repeat(ordersPerThread) {
                        val request = OrderCreateRequest(productNumbers = listOf(productNumber))
                        orderService.createOrder(request, LocalDateTime.now())
                    }
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        executor.shutdown()

        // 최종 재고 확인
        val finalStock = stockRepository.findByProductNumber(productNumber)?.quantity
        val expectedStock = initialStock - (numberOfThreads * ordersPerThread * orderQuantity)
        assertThat(finalStock).isEqualTo(expectedStock)
    }

    @Test
    fun `단일 스레드 주문 생성 테스트`() {
        val product1 = createProduct("001", HANDMADE, 4000L)
        val product2 = createProduct("002", HANDMADE, 4500L)
        val product3 = createProduct("003", HANDMADE, 7000L)
        productRepository.saveAll(listOf(product1, product2, product3))

        val productNumber = "001"
        val initialStock = 100
        stockRepository.save(Stock.create(productNumber, initialStock))

        repeat(100) {
            val request = OrderCreateRequest(productNumbers = listOf(productNumber))
            orderService.createOrder(request, LocalDateTime.now())
        }

        val finalStock = stockRepository.findByProductNumber(productNumber)?.quantity
        val expectedStock = 0
        assertThat(finalStock).isEqualTo(expectedStock)
    }

    private fun createProduct(productNumber: String, productType: ProductType, price: Long): Product = Product(
        productNumber = productNumber,
        type = productType,
        sellingStatus = SELLING,
        name = "메뉴이름",
        price = price,
    )
}
