package com.blackmagic.camera

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Basic unit tests for project foundation verification.
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun applicationPackage_isCorrect() {
        val expectedPackage = "com.blackmagic.camera"
        assertTrue(expectedPackage.isNotEmpty())
        assertEquals("com.blackmagic.camera", expectedPackage)
    }
}
