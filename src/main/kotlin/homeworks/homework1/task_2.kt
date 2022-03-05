package homeworks.homework1

import kotlin.system.exitProcess

fun sieveOfEratosthenes(bound: Int): List<Int> {
    require(bound >= 0) { "Bound must be non-negative" }

    val numbers = (2..bound).toMutableList()

    for (current in numbers) {
        if (current != 0) {
            for (i in (current * current)..bound step current) {
                numbers[i - 2] = 0
            }
        }
    }

    return numbers.filter { it > 0 }
}

fun main() {
    println("Enter the number:")
    val number = readln().toIntOrNull()

    if (number == null) {
        println("It seems you enter not a number. :(")
        exitProcess(0)
    }
    if (number < 0) {
        println("Enter the positive number, please. :)")
        exitProcess(0)
    }
    if (number < 2)
        println("There are no primes not exceeding $number")
    else {
        val primeNumbers = sieveOfEratosthenes(number)
        println("All primes not exceeding $number:")
        println(primeNumbers.joinToString(separator = " "))
    }
}
