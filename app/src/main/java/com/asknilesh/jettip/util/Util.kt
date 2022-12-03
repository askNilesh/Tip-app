package com.asknilesh.jettip.util

fun calculateTip(totalBill: Double, tipPercent: Float): Double {
  return if (totalBill > 1) {
    (totalBill * tipPercent) / 100
  } else 0.0
}

fun calculateTotalPerPerson(totalBill: Double, splitBy: Int, tipPercent: Float): Double {
  val bill = calculateTip(totalBill, tipPercent) + totalBill
  return bill / splitBy
}