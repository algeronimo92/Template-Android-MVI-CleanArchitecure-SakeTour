package com.alangeronimo.saketour.presentation.viewmodel

import app.cash.turbine.test
import com.alangeronimo.domain.model.SakeShop
import com.alangeronimo.domain.repository.SakeShopRepository
import com.alangeronimo.domain.useCase.GetSakeShopsUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SakeShopViewModelTest {
    private lateinit var fakeUseCase: GetSakeShopsUseCase
    private lateinit var viewModel: SakeShopViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    private val fakeShops =
        listOf(
            SakeShop("Kyoto Sake", "Sake tradicional", null, 4.5f, "Kyoto", "", ""),
            SakeShop("Tokyo Brew", "Moderno y elegante", null, 4.2f, "Tokyo", "", ""),
        )

    @Before
    fun setup() {
        val fakeRepo =
            object : SakeShopRepository {
                override suspend fun getSakeShops(): List<SakeShop> = fakeShops
            }
        fakeUseCase = GetSakeShopsUseCase(fakeRepo)
        viewModel = SakeShopViewModel(fakeUseCase)
    }

    @Test
    fun `loadSakeShops should emit loading and then data`() =
        runTest {
            viewModel.state.test {
                advanceUntilIdle()

                val result = awaitItem()
                assertFalse(result.isLoading)
                assertEquals(fakeShops, result.sakeShops)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `loadSakeShops should emit error when use case fails`() =
        runTest {
            val failingRepo =
                object : SakeShopRepository {
                    override suspend fun getSakeShops(): List<SakeShop> {
                        throw RuntimeException("API failed")
                    }
                }

            val failingUseCase = GetSakeShopsUseCase(failingRepo)
            val viewModel = SakeShopViewModel(failingUseCase)

            viewModel.state.test {
                awaitItem() // initial
                awaitItem() // loading
                val errorState = awaitItem()

                assertNotNull(errorState.error)
                assertTrue(errorState.error!!.contains("API failed"))

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `findShopByName should return correct shop`() =
        runTest {
            advanceUntilIdle()
            val shop = viewModel.findShopByName("Tokyo Brew")
            assertNotNull(shop)
            assertEquals("Tokyo Brew", shop?.name)
        }

    @Test
    fun `findShopByName should return null when shop doesn't exist`() =
        runTest {
            advanceUntilIdle()
            val shop = viewModel.findShopByName("Non Existing Shop")
            assertEquals(null, shop)
        }
}
