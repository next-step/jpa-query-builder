package study

class Car (
    private val name: String = "",
    private val price: Int = 0
) {

    @PrintView
    fun printView() {
        println("자동차 정보를 출력합니다.")
    }

    fun testGetName() = "test : $name"
    fun testGetPrice() = "test : $price"
}
