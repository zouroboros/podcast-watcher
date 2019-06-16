/*
This file is part of FeedWatcher.

FeedWatcher is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

FeedWatcher is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with FeedWatcher. If not, see <https://www.gnu.org/licenses/>.
Copyright 2019 Zouroboros
 */
package me.murks.feedwatcher

import junit.framework.Assert.*
import org.junit.Test

/**
 * @author zouroboros
 */
class TextsTests {
    @Test
    fun findUrlTest1() {
        val ddgUrlString = "https://ddg.gg"
        val ddg = Texts.findUrl(ddgUrlString)
        assertNotNull("A URL string should result in a valid url", ddg)
        assertEquals("findUrl on a valid string should produce the string as URL",
                ddg.toString(), ddgUrlString)
    }

    @Test
    fun findUrlTest2() {
        val urlWithText = "Hello: https://github.com"
        val url = Texts.findUrl(urlWithText)
        assertNotNull("A text with a url should produce the url", url)
        assertEquals("Complete URL should be extracted",
                url.toString(), "https://github.com")
    }

    @Test
    fun findUrlTest3() {
        val textWithNoUrl = "hello:/"

        val url = Texts.findUrl(textWithNoUrl)

        assertNull("Text without an url shoud not produce a url", url)
    }
}