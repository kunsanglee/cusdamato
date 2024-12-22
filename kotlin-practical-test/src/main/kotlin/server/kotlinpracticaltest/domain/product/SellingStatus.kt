package server.kotlinpracticaltest.domain.product

enum class SellingStatus(
    val text: String
) {
    SELLING("판매중"),
    HOLD("보류"),
    STOP_SELLING("판매중지")
    ;

    companion object {
        fun forDisplay(): Collection<SellingStatus> = listOf(SELLING, HOLD)
    }
}
