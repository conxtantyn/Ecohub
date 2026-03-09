package com.ecohub.heater.data.interactor

import com.ecohub.heater.data.fake.FakePersistenceInteractor
import com.ecohub.heater.domain.model.Channel
import com.ecohub.heater.domain.model.Metrics
import com.ecohub.heater.domain.model.Response
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class HeaterInteractorDelegateTest {
    private lateinit var persistence: FakePersistenceInteractor
    private lateinit var interactor: HeaterInteractorDelegate
    private val uuid = "test-heater"

    @BeforeTest
    fun setup() {
        persistence = FakePersistenceInteractor()
        interactor = HeaterInteractorDelegate(uuid, persistence)
    }

    @Test
    fun `get returns current metrics`() = runTest {
        interactor.update(20f, 1)
        
        val result = interactor.get()
        assertEquals(20f, result.temperature)
        assertEquals(1, result.version)
    }

    @Test
    fun `update locally increments version and updates temperature`() = runTest {
        interactor.update(25f, 5)
        
        val result = interactor.get()
        assertEquals(25f, result.temperature)
        assertEquals(5, result.version)
        assertEquals(Channel.LOCAL, result.channel)
    }

    @Test
    fun `synchronize updates remote metrics`() = runTest {
        interactor.synchronize(22f, 10)
        
        val result = interactor.get()
        assertEquals(22f, result.temperature)
        assertEquals(10, result.version)
        assertEquals(Channel.REMOTE, result.channel)
    }

    @Test
    fun `resolve clears conflict and updates to resolved value`() = runTest {
        interactor.synchronize(20f, 1)
        interactor.update(21f, 1)
        
        val resolveTo = Metrics("dev", Channel.LOCAL, 25f, 2000L, 2)
        val response = interactor.resolve(resolveTo)
        
        assertIs<Response.Success>(response)
        assertEquals(25f, response.metrics.temperature)
        assertEquals(1, response.metrics.version)
        
        val current = interactor.get()
        assertEquals(25f, current.temperature)
        assertEquals(1, current.version)
    }
}
