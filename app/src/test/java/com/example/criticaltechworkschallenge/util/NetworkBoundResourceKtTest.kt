package com.example.criticaltechworkschallenge.util

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class NetworkBoundResourceTest {
    @Test
    fun `should emit initial data from query flow when shouldFetch() returns false`() =
        runBlocking {
            // Given
            val queryData = "Initial Data"
            val queryFlow = flowOf(queryData)
            val fetch: suspend () -> String = mockk()
            val saveFetchResult: suspend (String) -> Unit = mockk()
            val shouldFetch: (String) -> Boolean = { false }

            // When
            val result = networkBoundResource(
                query = { queryFlow },
                fetch = fetch,
                saveFetchResult = saveFetchResult,
                shouldFetch = shouldFetch
            ).toList()

            // Then
            assertEquals(queryData, result.first().data)
        }

    @Test
    fun `should emit Resource Loading followed by Resource Success after fetching data when shouldFetch() returns true`() =
        runBlocking {
            // Given
            val queryData = "Initial Data"
            val queryFlow = flowOf(queryData)
            val fetchedData = "Fetched Data"
            val fetch: suspend () -> String = mockk()
            val saveFetchResult: suspend (String) -> Unit = mockk()
            val shouldFetch: (String) -> Boolean = { true }

            coEvery { fetch.invoke() } returns fetchedData
            coEvery { saveFetchResult.invoke(fetchedData) } throws Exception("saveFetchResult failed")

            // When
            val result = networkBoundResource(
                query = { queryFlow },
                fetch = fetch,
                saveFetchResult = saveFetchResult,
                shouldFetch = shouldFetch
            ).toList()


            // Then
            assertEquals(
                listOf(
                    Resource.Loading(queryData),
                    Resource.Success(queryData)
                ).first().javaClass, result.first().javaClass
            )
            assertEquals(
                listOf(
                    Resource.Loading(queryData),
                    Resource.Success(queryData)
                )[1].javaClass, result[1].javaClass
            )
        }

    @Test
    fun `should emit Resource Loading followed by Resource Success when there is an exception during fetching data`() =
        runBlocking {
            // Given
            val queryData = "Initial Data"
            val queryFlow = flowOf(queryData)
            val exception = Exception("Fetch Error")
            val fetch: suspend () -> String = {
                throw exception
            }
            val saveFetchResult: suspend (String) -> Unit = mockk()
            val shouldFetch: (String) -> Boolean = { true }

            // When
            val result = networkBoundResource(
                query = { queryFlow },
                fetch = { fetch() },
                saveFetchResult = saveFetchResult,
                shouldFetch = shouldFetch
            ).toList()

            // Then
            assertEquals(
                listOf(
                    Resource.Loading(queryData),
                    Resource.Success(queryData)
                ).first().javaClass, result.first().javaClass
            )
            assertEquals(
                listOf(
                    Resource.Loading(queryData),
                    Resource.Success(queryData)
                )[1].javaClass, result[1].javaClass
            )
        }


    @Test
    fun `should call saveFetchResult() with the fetched data when shouldFetch() returns true`() =
        runBlocking {
            // Given
            val queryData = "Initial Data"
            val queryFlow = flowOf(queryData)
            val fetchedData = "Fetched Data"
            val fetch: suspend () -> String = mockk()
            val saveFetchResult: suspend (String) -> Unit = mockk()
            val shouldFetch: (String) -> Boolean = { true }
            coEvery { fetch.invoke() } returns fetchedData
            coEvery { saveFetchResult.invoke(fetchedData) } just runs

            // When
            networkBoundResource(
                query = { queryFlow },
                fetch = fetch,
                saveFetchResult = saveFetchResult,
                shouldFetch = shouldFetch
            ).toList()

            // Then
            coVerify { saveFetchResult.invoke(fetchedData) }
        }
}
