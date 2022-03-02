package homeworks.homework1

import kotlin.system.exitProcess

fun sieveOfEratosthenes(bound: Int): List<Int> {
    val isPrimeNumber = MutableList(bound) { true }
    isPrimeNumber[0] = false

    var number = 2
    while (number * number <= bound) {
        if (isPrimeNumber[number - 1]) {
            for (i in (number * number)..bound step number) {
                isPrimeNumber[i - 1] = false
            }
        }
        number++
    }

    return isPrimeNumber.mapIndexedNotNull { index, isPrime -> if (isPrime) index + 1 else null }
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
        print("All primes not exceeding $number: \n${primeNumbers.joinToString(separator = " ")}")
    }
}
