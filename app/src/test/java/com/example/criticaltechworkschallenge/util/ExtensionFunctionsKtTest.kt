package com.example.criticaltechworkschallenge.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.format.DateTimeParseException

class StringExtensionsTest {

    @Test
    fun `formatDate() should return formatted date string`() {
        val dateString = "2023-04-26T13:30:00.000Z"
        val expected = "26/04/2023 13:30:00"

        val actual = dateString.formatDate()

        assertEquals(expected, actual)
    }

    @Test(expected = DateTimeParseException::class)
    fun `formatDate() should throw exception on invalid date string`() {
        val invalidDateString = "invalid-date-string"

        invalidDateString.formatDate()
    }

}