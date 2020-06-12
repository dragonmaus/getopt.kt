package us.dragonma.getopt

import kotlin.test.Test
import kotlin.test.assertEquals

class OptionTest {
    @Test
    fun testNoneToString() {
        assertEquals("Option()", Option().toString())
    }

    @Test
    fun testFlagToString() {
        assertEquals("Option(flag='a')", Option('a').toString())
    }

    @Test
    fun testOptionToString() {
        assertEquals("Option(flag='a', arg=\"foo\")", Option('a', "foo").toString())
    }
}
