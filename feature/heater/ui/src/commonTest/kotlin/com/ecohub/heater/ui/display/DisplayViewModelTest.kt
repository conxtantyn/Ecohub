package com.ecohub.heater.ui.display

import com.ecohub.heater.domain.model.Channel
import com.ecohub.heater.domain.model.Metrics
import com.ecohub.heater.domain.model.Response
import com.ecohub.heater.domain.usecase.ObserveUseCase
import com.ecohub.heater.ui.fake.FakeHeaterInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class DisplayViewModelTest {
    private val interactor = FakeHeaterInteractor()
    private val observe = ObserveUseCase(interactor)
    private lateinit var viewModel: DisplayViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = DisplayViewModel(observe)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Default`() {
        assertEquals(DisplayViewModel.State.Default, viewModel.state.value)
    }

    @Test
    fun `emits Success when observe returns Success`() = runTest {
        viewModel.invoke()
        val metrics = Metrics("dev", Channel.LOCAL, 22f, 1000L, 1)
        
        (interactor.state() as MutableStateFlow).value = Response.Success(metrics)
        
        val state = viewModel.state.value
        assertIs<DisplayViewModel.State.Success>(state)
        assertEquals(22f, state.metrics.temperature)
    }

    @Test
    fun `emits Conflict when observe returns Conflict`() = runTest {
        viewModel.invoke()
        val target = Metrics("dev", Channel.REMOTE, 25f, 1000L, 2)
        val current = Metrics("dev", Channel.LOCAL, 22f, 1000L, 2)
        
        (interactor.state() as MutableStateFlow).value = Response.Conflict(target, current)
        
        val state = viewModel.state.value
        assertIs<DisplayViewModel.State.Conflict>(state)
        assertEquals(25f, state.target.temperature)
        assertEquals(22f, state.current.temperature)
    }

    @Test
    fun `emits Default when observe returns Empty`() = runTest {
        viewModel.invoke()
        (interactor.state() as MutableStateFlow).value = Response.Empty
        assertEquals(DisplayViewModel.State.Default, viewModel.state.value)
    }
}
