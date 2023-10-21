package com.darekbx.hexview

import org.junit.Test

import java.io.ByteArrayInputStream

class StreamTest {

    @Test
    fun test_read() {
        // Given
        val inputStream = ByteArrayInputStream("one1two2testfour".toByteArray())

        // Then
        val buffer = ByteArray(4)
        inputStream.read(buffer)
        assert(String(buffer) == "one1")

        inputStream.read(buffer)
        assert(String(buffer) == "two2")

        inputStream.read(buffer)
        assert(String(buffer) == "test")

        inputStream.read(buffer)
        assert(String(buffer) == "four")

    }
}