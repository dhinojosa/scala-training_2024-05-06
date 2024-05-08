package com.xyzcorp

case class Employee(firstName: String, lastName: String, department: Department) {
    //ancillary constructor
    def this(firstName:String, lastName:String) = this(firstName, lastName, new Department("Human Resources"))
}

class Department (val name: String)
object Department {
    def apply(name:String): Department = {
        if (name == null) throw new IllegalArgumentException("Don't do that")
        new Department(name)
    }
}

//class, object, trait

//static
object Runner {
    //Unit - void
    def main(args: Array[String]): Unit = {
        import java.lang.Math.{min => javaMin}

        val result = javaMin(30, 10)
        val guido = Employee("Guido", "Van Rossum", Department("Tires"))
        val guido2 = Employee("Guido", "Van Rossum", Department("Tires"))
        val james = Employee("James", "Gosling", Department("Engineering"))
        println(james)
        println(guido)

        lazy val a = {println("evaluated"); 5}
        println(a)

        val result1 = 1.+(3)
        //infix allows us to format method calls without formal . and parens
        val result2 = 1 + 3

        println(result2)

        println(guido.firstName)
        println(guido.department)
        println(guido)

        println(guido == guido2) //== is equals()

        val Employee(fn, ln, _) = guido
        println(fn)
        println(ln)

        val ryan = james.copy(firstName = "Ryan", department = Department("Kitchen"))
        println(james)
        println(ryan)
    }
}
