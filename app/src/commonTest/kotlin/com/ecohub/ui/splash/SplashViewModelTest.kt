package com.ecohub.ui.splash

import com.ecohub.heater.domain.usecase.DeviceUsecase
import com.ecohub.ui.fake.FakeDeviceRepository
import com.ecohub.ui.fake.FakeDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {
    private val repository = FakeDeviceRepository()
    private val testDispatcher = UnconfinedTestDispatcher()
    private val dispatcher = FakeDispatcher(testDispatcher)
    private val usecase = DeviceUsecase(dispatcher, repository)
    private lateinit var viewModel: SplashViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SplashViewModel(usecase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke with name emits loading then success when device is initialized`() = runTest {
        val name = "MyHeater"
        val expectedUuid = "uuid-$name"
        
        viewModel.invoke(name)
        
        val state = viewModel.state.value
        assertIs<SplashViewModel.State.Success>(state)
        assertEquals(expectedUuid, state.uuid)
    }

    @Test
    fun `invoke with name emits loading then success when device already exists`() = runTest {
        val name = "MyHeater"
        val existingUuid = "existing-uuid"
        repository.device = com.ecohub.heater.domain.model.Device(uuid = existingUuid, name = name)
        
        viewModel.invoke(name)
        
        val state = viewModel.state.value
        assertIs<SplashViewModel.State.Success>(state)
        assertEquals(existingUuid, state.uuid)
    }

    @Test
    fun `invoke emits error state when repository fails`() = runTest {
        repository.shouldFail = true
        val name = "MyHeater"
        
        viewModel.invoke(name)
        
        val state = viewModel.state.value
        assertIs<SplashViewModel.State.Error>(state)
        assertEquals("Init Error", state.error.message)
    }
}
