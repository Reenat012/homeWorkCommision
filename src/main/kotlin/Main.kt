fun main() {
    val typeCard = "MasterCard"
    val sumRemittanceMonthAgo = 3_000_000
    val sumRemittanceNew = 120_000
    printSumRemmittance(typeCard, sumRemittanceMonthAgo, sumRemittanceNew) //расчет комиссии
}

//проверяем входит ли сумма переводов в лимит в сутки
fun isExceedLimitDay(typeCard: String, sumRemittanceNew: Int): Boolean {
    return when (typeCard) {
        "MasterCard", "Maestro", "Visa", "Мир" -> if (sumRemittanceNew in 0..150_000) true else false
        "VK Pay" -> if (sumRemittanceNew in 0..15_000) false else true
        else -> false
    }
}

//проверяем входит ли сумма переводов в лимит в месяц
fun isExceedLimitMonth(typeCard: String, sumRemittanceMonthAgo: Int, sumRemittanceNew: Int): Boolean {
    return when (typeCard) {
        "MasterCard", "Maestro", "Visa", "Мир" -> if (sumRemittanceMonthAgo + sumRemittanceNew <= 600_000) true else false
        "VK Pay" -> if (sumRemittanceMonthAgo + sumRemittanceNew <= 40_000) true else false
        else -> false
    }
}

//рассчитываем сумму комиссии, если превышены лимиты возвращает -1 или -2
fun calcCommission(typeCard: String = "VK Pay", sumRemittanceMonthAgo: Int = 0, sumRemittanceNew: Int): Int {
    return if (isExceedLimitDay(typeCard, sumRemittanceNew)) {
        if (isExceedLimitMonth(typeCard, sumRemittanceMonthAgo, sumRemittanceNew)) {
            when (typeCard) {
                "MasterCard", "Maestro" -> if ((sumRemittanceMonthAgo + sumRemittanceNew) in 300..75_000) 0
                else (sumRemittanceNew * 0.006).toInt() + 20

                "Visa", "Мир" -> if ((sumRemittanceNew * 0.0075) < 35) 35
                else (sumRemittanceNew * 0.0075).toInt()

                else -> 0
            }
        } else return -1
    } else -2
}

//суммируем разовый перевод и комиссию, если вывод -1 или -2 исчерпаны лимиты
fun sumRemittanceWithCommision(
    typeCard: String = "VK Pay",
    sumRemittanceMonthAgo: Int = 0,
    sumRemittanceNew: Int
): Int {
    return if (calcCommission(typeCard, sumRemittanceMonthAgo, sumRemittanceNew) == -1) -1
    else if (calcCommission(typeCard, sumRemittanceMonthAgo, sumRemittanceNew) == -2) -2
    else sumRemittanceNew + calcCommission(typeCard, sumRemittanceMonthAgo, sumRemittanceNew)
}

//вывод перевода с комиссией, с условием превышения лимита
fun printSumRemmittance(typeCard: String, sumRemittanceMonthAgo: Int, sumRemittanceNew: Int) {
    if (sumRemittanceWithCommision(typeCard, sumRemittanceMonthAgo, sumRemittanceNew) == -1)
        println("Лимит переводов в этом месяце исчерпан")
    else if (sumRemittanceWithCommision(typeCard, sumRemittanceMonthAgo, sumRemittanceNew) == -2)
        println("Лимит переводов в сутки исчерпан")
    else println(sumRemittanceWithCommision(typeCard, sumRemittanceMonthAgo, sumRemittanceNew))
}

