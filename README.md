# EcoHub Heat Pump Challenge

This project implements a mobile feature for monitoring and controlling Heat Pumps, handling concurrent updates from homeowners and technicians.

## Features

- **Temperature Dashboard**: Real-time monitoring of the heat pump temperature.
- **Remote Control**: homeowners can adjust the target temperature using increments/decrements.
- **Collaborative Mode**:
    - **ON (Auto-Resolve)**: Automatically prioritizes technician updates when conflicts occur, providing immediate feedback.
    - **OFF (Manual Resolve)**: prompts the user to either "Keep Theirs" or "Overwrite" when a version conflict is detected.
- **Technician Simulator**: A background service that simulates remote calibration every 15 seconds.

## Architecture Decisions

- **Layered Architecture**: Decoupled Domain, Data, and UI layers using UseCases and Repositories.
- **Koin for DI**: Clean dependency management and scope handling.
- **State Management**: Using Kotlin Flows (`StateFlow`, `SharedFlow`) and Jetpack Compose for reactive UI.
- **Concurrency**: Managed using Coroutines with appropriate dispatchers and `SupervisorJob` for background simulation resilience.
- **Versioning Strategy**: Implemented a monotonic versioning system to detect and handle race conditions accurately.

## Implementation Details

### Conflict Resolution
The core logic resides in `ObserveUseCase`, which monitors both the device state and the current collaboration mode. When a conflict (outdated version) is detected:
1. In **Automatic Mode**, it resolves in favor of the technician and notifies the user via a Snackbar.
2. In **Manual Mode**, it bubbles the conflict up to the UI, triggering a resolution dialog.

### Technician Simulation
`HeatService` runs in the background, periodically updating the "Remote" channel data and incrementing the version, ensuring the race condition scenario is always active during testing.

## Setup Instructions

1. Open the project in Android Studio or IntelliJ IDEA.
### Build and Run

1. Generate Koin dependencies:
   ```bash
   ./gradlew generateDepencencyMain
   ```
2. Build the Android app:
   ```bash
   ./gradlew :app:assembleDebug
   ```
3. Run the application on an emulator or physical device.

## Trade-offs
- **Simulation Scope**: The "Remote" technician update is simulated entirely in-app for the purpose of the challenge. In a real-world scenario, this would be an external API or MQTT push.
- **UI Feedback**: Used standard Material 3 Snapbars for notifications to maintain a clean and idiomatic Android feel.

## AI Tool Usage Log

In accordance with the challenge requirements, this log documents the use of AI assistance during the development of this feature.

### How
I used **Antigravity** (an agentic AI coding assistant) as a pair programmer. The AI performed the following roles:
- **Researcher**: Automated analysis of the codebase to identify race condition root causes.
- **Developer**: Implemented code changes, created unit tests, and managed dependencies.
- **QA**: Designed and executed reproduction test cases to verify fixes and ensure no regressions.

### Why
- **Efficiency**: Rapidly navigated the multi-module project structure to locate relevant logic and dependencies.
- **Correctness**: Used the AI's ability to simulate complex concurrency scenarios to implement a robust fix for a subtle race condition.
- **Quality**: Ensured comprehensive test coverage for both existing and new components (`ControllerViewModel`, `SplashViewModel`).

### Where
- `ControllerViewModel.kt`: Implemented `collectLatest` and version-based state filtering to resolve remote/local update conflicts.
- `ControllerViewModelTest.kt`: Refactored and cleaned up test suites post-feature adjustments.
- `SplashViewModelTest.kt`: Authored a complete suite of unit tests along with necessary fakes (`FakeDeviceRepository`, `FakeDispatcher`).
- `app/build.gradle.kts`: Configured project-level test dependencies.