package com.example.tipcalculator.util

fun calculateAmount(billAmount: Double, percentage: Int): Double {

    return if (billAmount > 1 && billAmount.toString().isNotEmpty())
        (billAmount * percentage) / 100 else 0.0

}


fun calculateTotalPerPerson(
    billAmount: Double,
    split: Int,
    percentage: Int,
) :Double {
var bill = calculateAmount(billAmount = billAmount, percentage = percentage) + billAmount
    return (bill/split)
}