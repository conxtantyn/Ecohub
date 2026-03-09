package com.ecohub.preference.interactor

import com.ecohub.common.interactor.PersistenceInteractor
import com.russhwolf.settings.MapSettings
import com.russhwolf.settings.ObservableSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PersistenceInteractorDelegateTest {
    private lateinit var settings: ObservableSettings
    private lateinit var interactor: PersistenceInteractor

    @Serializable
    data class User(val name: String, val age: Int)

    @BeforeTest
    fun setup() {
        settings = MapSettings()
        interactor = PersistenceInteractorDelegate(Json.Default, settings)
    }

    @Test
    fun `set and get primitive values`() {
        interactor.set("username", "alice")
        interactor.set("loggedIn", true)
        interactor.set("age", 30)

        assertEquals("alice", interactor.get("username", typeOf<String>()))
        assertEquals(true, interactor.get("loggedIn", typeOf<Boolean>()))
        assertEquals(30, interactor.get("age", typeOf<Int>()))
    }

    @Test
    fun `set and get serializable object`() {
        val user = User("bob", 25)
        interactor.set("user", user)

        val restored: User? = interactor.get("user", typeOf<User>())
        assertEquals(user, restored)
    }

    @Test
    fun `observe emits initial current value`() = runTest {
        interactor.set("count", 10)
        val flow = interactor.observe<Int>("count", typeOf<Int>())
        
        val initial = flow.first()
        assertEquals(10, initial)
    }

    @Test
    fun `observe emits new values`() = runTest {
        val flow = interactor.observe<Int>("count", typeOf<Int>())
        val results = mutableListOf<Int?>()
        val collector = flow.onEach { results.add(it) }.launchIn(this)
        
        testScheduler.runCurrent()
        
        interactor.set("count", 1)
        interactor.set("count", 2)
        
        testScheduler.advanceUntilIdle()

        assertTrue(results.contains(1))
        assertTrue(results.contains(2))

        collector.cancel()
    }

    @Test
    fun `observe multiple observers`() = runTest {
        val flow1 = interactor.observe<Int>("count", typeOf<Int>())
        val flow2 = interactor.observe<Int>("count", typeOf<Int>())

        val results1 = mutableListOf<Int?>()
        val results2 = mutableListOf<Int?>()

        val job1 = flow1.onEach { results1.add(it) }.launchIn(this)
        val job2 = flow2.onEach { results2.add(it) }.launchIn(this)

        interactor.set("count", 100)
        
        // Give some time for flows to collect
        testScheduler.advanceUntilIdle()

        assertEquals(100, results1.last())
        assertEquals(100, results2.last())

        job1.cancel()
        job2.cancel()
    }

    @Test
    fun `remove deletes key`() {
        interactor.set("session", "token123")
        assertEquals(
            expected = "token123",
            actual = interactor.get("session", typeOf<String>())
        )
        interactor.remove("session")
        assertNull(interactor.get("session", typeOf<String>()))
    }
}