package server.kotlinpracticaltest.domain.product

enum class ProductType(val text: String) {
    HANDMADE("제조 음료"),
    BOTTLE("병 음료"),
    BAKERY("베이커리"),
    ;

    companion object {
        fun containsType(type: ProductType): Boolean {
            return setOf(BOTTLE, BAKERY).contains(type)
        }
    }
}
