package ru.netology

fun main() {
    println(calcAmount("Mastercard", 500000.0, 100001.0))
}

fun calcAmount(cartType: String = "VK Pay", previousTransfers: Double = 0.0, transferAmount: Double): Double {
    val commission: Double = when (cartType) {
        "Mastercard", "Maestro" -> getCommissionMastercardMaestro(previousTransfers, transferAmount) // Оплата комиссии необходима только в случае если превышен лимит в 600000 в месяц
        "Visa", "Мир" -> getCommissionVisaMir(transferAmount) // Всегда одна и та же комиссия, лимит не влияет ни на что
        "VK Pay" -> 0.0 // Всегда без комиссии
        else -> {
            println("Данный тип карты/счёта $cartType не подерживаются системой")
            return 0.0
        }
    }
    return transferAmount + commission
}

fun getCommissionMastercardMaestro(previousTransfers: Double, transferAmount: Double): Double {
    val limit = 600000
    val commission = 0.006
    val commissionPart = 20
    if (transferAmount + previousTransfers > limit) {
        return transferAmount * commission + commissionPart
    }
    return 0.0
}

fun getCommissionVisaMir(transferAmount: Double): Double {
    val commission = 0.0075
    val minCommission = 35.0
    if (transferAmount * commission > minCommission) {
        return transferAmount * commission
    }
    return minCommission
}