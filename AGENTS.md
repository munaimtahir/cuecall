# Repository Guidelines

## Project Structure & Module Organization
This repository is a single-module Android app rooted at `:app`. Main Kotlin sources live under `app/src/main/java/com/cuecall/app`, organized by feature and layer: `data/` for Room, Firestore, and repository implementations, `domain/` for models and use cases, `ui/` for Compose screens, components, and theme, plus `di/`, `navigation/`, `printer/`, and `sync/`. Resources are in `app/src/main/res`. Unit tests live in `app/src/test/java`; `app/src/androidTest/java` is present for instrumentation or Compose UI tests but is currently empty. Reference docs and QA checklists live in `docs/`.

## Build, Test, and Development Commands
Use the Gradle wrapper from the repo root:

- `./gradlew assembleDebug` builds the debug APK.
- `./gradlew installDebug` installs the debug build on a connected device or emulator.
- `./gradlew :app:testDebugUnitTest` runs JVM unit tests for the app module.
- `./gradlew :app:connectedDebugAndroidTest` runs instrumentation and Compose UI tests on a connected emulator or device.
- `./gradlew :app:pixel2Api30DebugAndroidTest` runs the full instrumentation suite on the managed emulator defined in Gradle.
- `./gradlew :app:testDebugUnitTest --tests "com.cuecall.app.domain.usecase.GenerateTokenUseCaseTest"` runs one test class.
- `./gradlew assembleRelease` creates a release APK for verification.

Android Studio Hedgehog+ and JDK 17 are the expected local toolchain.

## Coding Style & Naming Conventions
Follow Kotlin defaults: 4-space indentation, trailing commas only where already used, and concise expression-focused code. Keep package names lowercase (`com.cuecall.app.ui.screens.history`), types in `PascalCase`, functions and properties in `camelCase`, and test methods in backticked descriptive phrases. Match the existing layering: UI code stays in `ui/`, data access in `data/`, and business rules in `domain/`. Compose screen files typically pair a `Screen` and `ViewModel`.

## Testing Guidelines
Current unit tests use JUnit 4, MockK, `kotlinx-coroutines-test`, Turbine, and Room test helpers. Add or update tests beside the affected package under `app/src/test/java`. Name files with the `*Test.kt` suffix, and prefer focused behavior-based test names such as ``fun `fails when service not found`()``. Run `./gradlew :app:testDebugUnitTest` before opening a PR.

## Commit & Pull Request Guidelines
Recent history uses short, imperative subjects such as `Build green: debug APK compiling after ...`. Keep commit titles specific and scoped to one change. For pull requests, include a brief summary, testing notes, linked issue if applicable, and screenshots or recordings for UI changes.

## Security & Configuration Tips
Do not commit real Firebase secrets. `app/google-services.json` must match the target Firebase project, and `local.properties` should remain local-only. Before release, verify Firestore/Auth configuration and run the smoke checklist in `docs/SMOKE_TEST_CHECKLIST.md`.
