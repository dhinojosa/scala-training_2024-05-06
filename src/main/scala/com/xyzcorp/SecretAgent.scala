package com.xyzcorp

class SecretAgent(val name: String) {
    def shoot(n: Int) {
        SecretAgent.decrementBullets(n) //Can be imported and shortened
        //using import SecretAgent._
    }
}

object SecretAgent {
    //This is encapsulated!
    private var b: Int = 3000 //only available privately

    private def decrementBullets(count: Int) {  //only available privately
        if (b - count <= 0) b = 0
        else b = b - count
    }

    def bullets = b
}
