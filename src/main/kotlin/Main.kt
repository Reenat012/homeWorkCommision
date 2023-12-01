fun main() {
    val typeCard = "MasterCard"
    val sumRemittanceMonthAgo = 300
    val sumRemittanceNew = 100
    printSumRemmittance(typeCard, sumRemittanceMonthAgo, sumRemittanceNew) //расчет комиссии
}

//проверяем входит ли сумма переводов в лимит
fun isExceedLimit(typeCard: String, sumRemittanceMonthAgo: Int, sumRemittanceNew: Int): Boolean {
    return when (typeCard) {
        "MasterCard", "Maestro", "Visa", "Мир" -> if (sumRemittanceMonthAgo + sumRemittanceNew <= 60_000) true else false
        "VK Pay" -> if (sumRemittanceMonthAgo + sumRemittanceNew <= 40_000) true else false
        else -> false
    }
}

//рассчитываем сумму комиссии
fun calcCommission(typeCard: String = "VK Pay", sumRemittanceMonthAgo: Int = 0, sumRemittanceNew: Int): Int {
    return if (isExceedLimit(typeCard, sumRemittanceMonthAgo, sumRemittanceNew)) {
        when (typeCard) {
            "MasterCard", "Maestro" -> if (300 <= sumRemittanceNew && sumRemittanceNew <= 75000) 0
            else (sumRemittanceNew * 0.06).toInt() + 20

            "Visa", "Мир" -> if ((sumRemittanceNew * 0.75) < 35) 35
            else (sumRemittanceNew * 0.75).toInt()

            else -> 0
        }
    } else return -1
}

fun sumRemittanceWithCommision(typeCard: String = "VK Pay", sumRemittanceMonthAgo: Int = 0, sumRemittanceNew: Int): Int {
    return if (calcCommission(typeCard, sumRemittanceMonthAgo, sumRemittanceNew) == -1) -1
    else sumRemittanceNew + calcCommission(typeCard, sumRemittanceMonthAgo, sumRemittanceNew)
}

fun printSumRemmittance(typeCard: String, sumRemittanceMonthAgo: Int, sumRemittanceNew: Int) {
    if (sumRemittanceWithCommision(typeCard, sumRemittanceMonthAgo, sumRemittanceNew) == -1)
        println("Лимит переводов в этом месяце исчерпан")
    else println(sumRemittanceWithCommision(typeCard, sumRemittanceMonthAgo, sumRemittanceNew))
}

