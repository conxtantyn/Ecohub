package com.ecohub.heater.ui.controller

import com.ecohub.heater.domain.model.Mode
import com.ecohub.heater.domain.usecase.*
import com.ecohub.heater.ui.fake.FakeDispatcher
import com.ecohub.heater.ui.fake.FakeHeaterInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class ControllerViewModelTest {
    private val interactor = FakeHeaterInteractor()
    private val testDispatcher = UnconfinedTestDispatcher()
    private val dispatcher = FakeDispatcher(testDispatcher)
    
    private val observe = ObserveUseCase(interactor)
    private val modeObserver = ModeUsecase(interactor)
    private val modeUpdate = ModeUpdateUsecase(interactor)
    private val heatUpdate = HeatUpdateUsecase(dispatcher, interactor)
    private val localUpdate = LocalUpdateUsecase(dispatcher, heatUpdate, interactor)
    private val resolve = HeatResolveUseCase(interactor)
    
    private lateinit var viewModel: ControllerViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ControllerViewModel(
            rate = 0.5f,
            observe = observe,
            modeObserver = modeObserver,
            modeUpdate = modeUpdate,
            localUpdate = localUpdate,
            resolve = resolve
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial toggle mode updates interactor`() = runTest {
        viewModel.onToggleMode(true)
        assertEquals(Mode.AUTOMATIC, interactor.mode().first())
        
        viewModel.onToggleMode(false)
        assertEquals(Mode.MANUAL, interactor.mode().first())
    }

    @Test
    fun `increase button triggers local update`() = runTest {
        viewModel.onUpdate(increment = true)
        assertEquals(20.5f, interactor.get().temperature)
        assertEquals(2, interactor.get().version)
    }

    @Test
    fun `decrease button triggers local update`() = runTest {
        viewModel.onUpdate(increment = false)
        assertEquals(19.5f, interactor.get().temperature)
        assertEquals(2, interactor.get().version)
    }
}
