package server.kotlinpracticaltest.api.service.order

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import server.kotlinpracticaltest.api.controller.order.request.OrderCreateRequest
import server.kotlinpracticaltest.domain.order.OrderRepository
import server.kotlinpracticaltest.domain.orderproduct.OrderProductRepository
import server.kotlinpracticaltest.domain.product.Product
import server.kotlinpracticaltest.domain.product.ProductRepository
import server.kotlinpracticaltest.domain.product.ProductType
import server.kotlinpracticaltest.domain.product.ProductType.BAKERY
import server.kotlinpracticaltest.domain.product.ProductType.BOTTLE
import server.kotlinpracticaltest.domain.product.ProductType.HANDMADE
import server.kotlinpracticaltest.domain.product.SellingStatus.SELLING
import server.kotlinpracticaltest.domain.stock.Stock
import server.kotlinpracticaltest.domain.stock.StockRepository
import java.time.LocalDateTime
import kotlin.test.Test

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest(
    @Autowired private val orderService: OrderService,
    @Autowired private val orderRepository: OrderRepository,
    @Autowired private val productRepository: ProductRepository,
    @Autowired private val orderProductRepository: OrderProductRepository,
    @Autowired private val stockRepository: StockRepository
) {
    @AfterEach
    fun tearDown() {
        orderProductRepository.deleteAllInBatch()
        productRepository.deleteAllInBatch()
        orderRepository.deleteAllInBatch()
    }

    @Test
    fun `주문번호 리스트를 받아 주문을 생성한다`() {
        val product1 = createProduct("001", HANDMADE, 4000L)
        val product2 = createProduct("002", HANDMADE, 4500L)
        val product3 = createProduct("003", HANDMADE, 7000L)
        productRepository.saveAll(listOf(product1, product2, product3))

        val request = OrderCreateRequest(listOf("001", "002"))
        val registeredDateTime = LocalDateTime.now()

        val orderResponse = orderService.createOrder(request, registeredDateTime)

        with(orderResponse) {
            assertThat(this.id).isNotNull.isNotZero
            assertThat(this)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 8500)
            assertThat(this.products)
                .hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                    tuple("001", 4000L),
                    tuple("002", 4500L)
                )
        }
    }

    @Test
    fun `재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다`() {
        val product1 = createProduct("001", BOTTLE, 1000L)
        val product2 = createProduct("002", BAKERY, 3000L)
        val product3 = createProduct("003", HANDMADE, 5000L)
        productRepository.saveAll(listOf(product1, product2, product3))

        val stock1 = Stock.create("001", 2)
        val stock2 = Stock.create("002", 2)
        val stock3 = Stock.create("003", 1)
        stockRepository.saveAll(listOf(stock1, stock2, stock3))

        val request = OrderCreateRequest(listOf("001", "001", "002", "003"))
        val registeredDateTime = LocalDateTime.now()

        val orderResponse = orderService.createOrder(request, registeredDateTime)

        with(orderResponse) {
            assertThat(this.id).isNotNull.isNotZero
            assertThat(this)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 10000)
            assertThat(this.products)
                .hasSize(4)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                    tuple("001", 1000L),
                    tuple("001", 1000L),
                    tuple("002", 3000L),
                    tuple("003", 5000L)
                )
        }

        val stocks = stockRepository.findAll()
        assertThat(stocks)
            .hasSize(3)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("001", 0),
                tuple("002", 1),
                tuple("003", 0)
            )
    }

    @Test
    fun `중복되는 상품번호 리스트로 주문을 생성할 수 있다`() {
        val product1 = createProduct("001", HANDMADE, 4000L)
        val product2 = createProduct("002", HANDMADE, 4500L)
        val product3 = createProduct("003", HANDMADE, 7000L)
        productRepository.saveAll(listOf(product1, product2, product3))

        val request = OrderCreateRequest(listOf("001", "001"))
        val registeredDateTime = LocalDateTime.now()

        val orderResponse = orderService.createOrder(request, registeredDateTime)

        with(orderResponse) {
            assertThat(this.id).isNotNull.isNotZero
            assertThat(this)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 8000)
            assertThat(this.products)
                .hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                    tuple("001", 4000L),
                    tuple("001", 4000L)
                )
        }
    }

    private fun createProduct(
        productNumber: String,
        productType: ProductType,
        price: Long
    ): Product =
        Product(
            productNumber = productNumber,
            type = productType,
            sellingStatus = SELLING,
            name = "메뉴이름",
            price = price
        )
}
