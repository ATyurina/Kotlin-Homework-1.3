package ru.netology

import kotlin.math.max

fun main() {
    println(calcAmount("Visa", 500_000, 100_001))
}

fun calcAmount(cartType: String = "VK Pay", previousTransfers: Int = 0, transferAmount: Int): Int {
    val commission: Int = when (cartType) {
        "Mastercard", "Maestro" -> getCommissionMastercardMaestro(previousTransfers, transferAmount)
        "Visa", "Мир" -> getCommissionVisaMir(previousTransfers, transferAmount)
        "VK Pay" -> 0
        else -> {
            println("Данный тип карты/счёта $cartType не подерживаются системой")
            -1
        }
    }
    return if (commission == -1) 0 else transferAmount + commission
}

fun getCommissionMastercardMaestro(previousTransfers: Int, transferAmount: Int): Int {
    val limitFirst = 75_000
    val limitSecond = 600_000
    val commission = 0.006
    val commissionPart = 20
    return when(previousTransfers + transferAmount) {
        in 0 until limitFirst -> 0
        in limitFirst until limitSecond -> (transferAmount * commission + commissionPart).toInt()
        else -> {
            println("Лимит по переводам в этом месяце превышен")
            -1
        }
    }
}

fun getCommissionVisaMir(previousTransfers: Int, transferAmount: Int): Int {
    val limit = 600_000
    val commission = 0.0075
    val minCommission = 35
    return if (previousTransfers + transferAmount > limit) {
        println("Лимит по переводам в этом месяце превышен")
        -1
    } else {
        max(minCommission,(transferAmount * commission).toInt())
    }
}