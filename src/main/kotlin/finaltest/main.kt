package finaltest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalCoroutinesApi::class)
fun playGame(url: String, depth: Int, nProcess: Int) {
    val page = runBlocking { getPage(url) }
    val links = getLinks(page)
    val map = mutableMapOf(url to links)
    val path = runBlocking(Dispatchers.Default.limitedParallelism(nProcess)) {
        findHitler(map, depth, mutableListOf())
    }.reversed()
    println("")
    if (path == emptyList<String>()) {
        println("Adolf wasn't found :(")
    } else {
        println("And here's Adolf!")
        println(path.joinToString(separator = " -> ") + " -> " + HITLER_PAGE)
    }
}

fun main() {
    println("Let's play a game Clicks to Hitler!!\nEnter the following parameters:")
    while (true) {
        print("Search depth: ")
        val depth = readln().toInt()
        print("Number of processors: ")
        val nProcess = readln().toInt()
        print("Article title (optional): ")
        val title = readln()
        var url = "https://en.wikipedia.org/wiki/"
        if (title != "") {
            url += title.replace(" ", "_")
        } else {
            url = runBlocking { getUrlFromRedirect(url + "Special:Random") }
            println("Okay, we'll start from here: $url")
        }
        println("Let's search!")
        playGame(url, depth, nProcess)
        println("Do you want to play again? Type Y/N")
        val answer = readln().uppercase()
        if (answer == "N")
            break
    }
}
