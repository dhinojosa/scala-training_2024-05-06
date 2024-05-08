package com.xyzcorp

class Box[+A](val value: A) {
    def map[B](function: A => B):Box[B] = {
        new Box(function(value))
    }
    //a is contravariant (-) so we won't use that, but we can use another type [B]
    //but with a constraint and that is B has to be any supertype of [A]
    def replace[B >: A <: AnyRef](newValue:B):Box[B] = new Box(newValue)

    //a is covariant position (+)
    //value():A
}
