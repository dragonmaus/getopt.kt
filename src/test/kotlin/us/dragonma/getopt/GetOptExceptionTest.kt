package us.dragonma.getopt

import kotlin.test.Test
import kotlin.test.assertFailsWith

class GetOptExceptionTest {
    // @Test
    // fun testConstructor1() {
    //     assertFailsWith<GetOptException> { throw GetOptException() }
    // }

    @Test
    fun testConstructor2() {
        assertFailsWith<GetOptException> { throw GetOptException(message) }
    }

    // @Test
    // fun testConstructor3() {
    //     assertFailsWith<GetOptException> { throw GetOptException(message, Exception(message)) }
    // }

    // @Test
    // fun testConstructor4() {
    //     assertFailsWith<GetOptException> { throw GetOptException(Exception(message)) }
    // }

    companion object {
        private const val message = "test message"
    }
}
