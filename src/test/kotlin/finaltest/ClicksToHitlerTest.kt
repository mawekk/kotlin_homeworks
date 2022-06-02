package finaltest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ClicksToHitlerTest {
    @Test
    fun `getLinks test`() {
        val page = runBlocking { getPage("https://www.youtube.com/") }
        assertEquals(emptyList<String>(), getLinks(page))
    }

    @Test
    fun `redirect test`() {
        val exception =
            assertThrows<IllegalStateException> {
                runBlocking { getUrlFromRedirect("https://en.wikipedia.org/wiki/Napoli_(album)") }
            }
        assertEquals("Can't find location header", exception.message)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @MethodSource("inputForFind")
    fun `find test`(url: String, depth: Int, expected: MutableList<String>) {
        val page = runBlocking { getPage(url) }
        val links = getLinks(page)
        val map = mapOf(url to links)
        val path = runBlocking(Dispatchers.Default.limitedParallelism(depth)) {
            findHitler(map, depth, mutableListOf())
        }.reversed()
        assertEquals(expected, path)
    }

    companion object {
        @JvmStatic
        fun inputForFind() = listOf(
            Arguments.of("https://en.wikipedia.org/wiki/Adolf_Hitler", 1, emptyList<String>()),
            Arguments.of(
                "https://en.wikipedia.org/wiki/Propaganda_in_Nazi_Germany", 2,
                mutableListOf("https://en.wikipedia.org/wiki/Propaganda_in_Nazi_Germany")
            ),
            Arguments.of("https://en.wikipedia.org/wiki/Ivan_Konev", 1, emptyList<String>())
        )
    }
}
