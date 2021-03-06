package finaltest

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup

const val HITLER_PAGE = "https://en.wikipedia.org/wiki/Adolf_Hitler"

@OptIn(ExperimentalCoroutinesApi::class)
fun playGame(url: String, depth: Int, nProcess: Int) {
    val page = runBlocking { getPage(url) }
    val links = getLinks(page)
    val map = mapOf(url to links)
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

suspend fun getPage(url: String): String {
    val client = HttpClient(CIO) {
        install(HttpTimeout) {
            requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
            connectTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
        }
    }
    val response: HttpResponse = client.get(url)
    client.close()
    return response.bodyAsText()
}

fun getLinks(page: String): List<String> {
    val document = Jsoup.parse(page)
    val links = document.select("div#mw-content-text").select("a[href^=/wiki]")
    return links.map { "https://en.wikipedia.org" + it.attr("href") }.distinct()
}

suspend fun findHitler(
    links: Map<String, List<String>>,
    depth: Int,
    path: MutableList<String>
): MutableList<String> = coroutineScope {
    print(".")
    if (depth == 0)
        return@coroutineScope path
    if (links.keys.contains(HITLER_PAGE))
        return@coroutineScope path
    var parent: String? = null
    links.forEach {
        if (it.value.contains(HITLER_PAGE)) {
            parent = it.key
            return@forEach
        }
    }
    if (parent != null) {
        path.add(parent!!)
        return@coroutineScope path
    }
    val newLinks = mutableMapOf<String, List<String>>()
    val job = launch {
        links.values.forEach {
            it.forEach { link ->
                launch {
                    val page = getPage(link)
                    newLinks[link] = getLinks(page)
                }
            }
        }
    }
    job.join()

    val newPath = findHitler(newLinks, depth - 1, path)
    if (newPath != emptyList<String>()) {
        links.forEach {
            if (it.value.contains(newPath.last())) {
                newPath.add(it.key)
            }
        }
    }
    return@coroutineScope newPath
}

suspend fun getUrlFromRedirect(url: String): String {
    val client = HttpClient(CIO) {
        install(HttpTimeout) {
            requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
            connectTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
        }
        followRedirects = false
    }
    val response: HttpResponse = client.get(url)
    client.close()
    return response.headers["location"] ?: throw IllegalStateException("Can't find location header")
}
