package us.dragonma.getopt

import kotlin.test.*

class GetOptTest {
    @Test
    fun testGetIndex() {
        val args: Array<String> = arrayOf("-ab", "-cd", "foo", "bar")
        val g = GetOpt(args, "abc:d")

        assertEquals(0, g.index)
        assertEquals(Option('a'), g.next())
        assertEquals(0, g.index)
        assertEquals(Option('b'), g.next())
        assertEquals(1, g.index)
        assertEquals(Option('c', "d"), g.next())
        assertEquals(2, g.index)
        assertEquals(Option(), g.next())
        assertEquals(2, g.index)

        assertEquals(listOf("foo", "bar"), args.drop(g.index))
    }

    @Test
    fun testSetIndex() {
        val g = GetOpt(arrayOf("-a", "-b", "-c", "d", "foo", "bar"), "abc:d")

        g.index = 2
        assertEquals(Option('c', "d"), g.next())
        g.index = 0
        assertEquals(Option('a'), g.next())
    }

    @Test
    fun testHasNext() {
        val g = GetOpt(arrayOf("-a", "-b", "foo"), "ab")

        assertTrue(g.hasNext())
        assertEquals(Option('a'), g.next())
        assertTrue(g.hasNext())
        assertEquals(Option('b'), g.next())
        assertFalse(g.hasNext())
    }

    @Test
    fun testNext1() {
        val args: Array<String> = arrayOf("-abc", "foo", "-def", "bar")
        val g = GetOpt(args, "abc:d:ef")

        assertEquals(Option('a'), g.next())
        assertEquals(Option('b'), g.next())
        assertEquals(Option('c', "foo"), g.next())
        assertEquals(Option('d', "ef"), g.next())
        assertEquals(Option(), g.next())

        assertEquals(listOf("bar"), args.drop(g.index))
    }

    @Test
    fun testNext2() {
        val args: Array<String> = arrayOf("-a", "-", "-b", "bar")
        val g = GetOpt(args, "ab")

        assertEquals(Option('a'), g.next())
        assertEquals(Option(), g.next())

        assertEquals(listOf("-", "-b", "bar"), args.drop(g.index))
    }

    @Test
    fun testNext3() {
        val args: Array<String> = arrayOf("-a", "--", "-b", "bar")
        val g = GetOpt(args, "ab")

        assertEquals(Option('a'), g.next())
        assertEquals(Option(), g.next())

        assertEquals(listOf("-b", "bar"), args.drop(g.index))
    }

    @Test
    fun testBadOption() {
        val g = GetOpt(arrayOf("-a"), "b")

        assertFailsWith<GetOptException> { g.next() }

        g.index = 0
        try {
            g.next()
        } catch (e: GetOptException) {
            assertEquals("unknown option -- 'a'", e.message)
        }
    }

    @Test
    fun testMissingArg() {
        val g = GetOpt(arrayOf("-a"), "a:")

        assertFailsWith<GetOptException> { g.next() }

        g.index = 0
        try {
            g.next()
        } catch (e: GetOptException) {
            assertEquals("option requires an argument -- 'a'", e.message)
        }
    }
}
