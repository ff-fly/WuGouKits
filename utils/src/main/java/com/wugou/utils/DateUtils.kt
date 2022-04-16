package com.wugou.utils

fun getDaysOfMonth(year: Int, month: Int): Int {
    val dayOfFeb = if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
        29
    } else {
        28
    }

    var days = 30
    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
        days = 31
    }
    if (month == 2) {
        days = dayOfFeb
    }

    return days
}