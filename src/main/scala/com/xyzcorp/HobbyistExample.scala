package com.xyzcorp

import scala.util.Random

case class Hobbyist(firstName: String, lastName: String, hobbies: List[String])

object HobbyistGenerator {
    private val firstNames = List("Alice", "Bob", "Carol", "David", "Eve", "Frank", "Grace", "Henry", "Ivy", "Jack")
    private val lastNames = List("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor")
    private val possibleHobbies = List("Photography", "Cycling", "Hiking", "Painting", "Gardening", "Cooking", "Blogging", "Knitting", "Swimming", "Running")

    def generateHobbyistList(count: Int): List[Hobbyist] = {
        (1 to count).map { _ =>
            val firstName = firstNames(Random.nextInt(firstNames.length))
            val lastName = lastNames(Random.nextInt(lastNames.length))
            val hobbies = Random.shuffle(possibleHobbies).take(Random.nextInt(5) + 1) // Each person has between 1 and 5 hobbies
            Hobbyist(firstName, lastName, hobbies)
        }.toList
    }
}


object HobbyRunner extends App{
    val hobbyists = HobbyistGenerator.generateHobbyistList(10)
    println(hobbyists)
    private val allHobbies:List[String] = hobbyists.flatMap(hobbyist => hobbyist.hobbies)
    println(allHobbies.distinct)
}
