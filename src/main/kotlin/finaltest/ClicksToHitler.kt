package finaltest

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

const val HITLER_PAGE = "https://en.wikipedia.org/wiki/Adolf_Hitler"

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
