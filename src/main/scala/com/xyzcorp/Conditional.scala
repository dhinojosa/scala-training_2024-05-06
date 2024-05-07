package com.xyzcorp

object Conditional {
    def tenStatus(a: Int): String =
        if (a < 10) "Less than 10"
        else if (a > 10) "Greater than 10"
        else "It is 10!"
}
