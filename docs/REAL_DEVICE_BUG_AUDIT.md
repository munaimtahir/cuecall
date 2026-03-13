# Real Device Bug Audit

Date: March 11, 2026

## Audit summary

### 1. How clinic setup completion was determined before the fix
- There was no real clinic-setup completion model.
- Runtime flows treated `AppSettings.clinicId` in DataStore as the effective setup state.
- The clinic Room table existed, but token generation did not validate against a persisted `Clinic` row.
- There was no `setupCompleted` table, flag, or canonical validator.

### 2. Which table/entity/flag stored clinic setup state
- Effective pre-fix state lived in DataStore:
  - `app/src/main/java/com/cuecall/app/data/repository/SettingsRepositoryImpl.kt`
  - key: `clinic_id`
- The actual clinic profile row lived in Room:
  - `app/src/main/java/com/cuecall/app/data/local/entity/Entities.kt`
  - entity: `ClinicEntity`
- Pre-fix bug: these two sources were not kept in sync and token generation trusted only DataStore.

### 3. What token generation checked before the fix
- `GenerateTokenUseCase` checked:
  - `settings.clinicId` is non-blank
  - selected service exists
  - queue day is created via `getOrCreateQueueDay`
  - Firestore sequence can be reserved
- It did not validate:
  - that a persisted clinic row exists
  - that clinic minimum fields are saved
  - that setup data and `settings.clinicId` match
  - that the selected service is active
- Pre-fix code:
  - `app/src/main/java/com/cuecall/app/domain/usecase/UseCases.kt`

### 4. How Counter was modeled before the fix
- Counter used `serviceIdsJson` in Room and `serviceIds: List<String>` in domain.
- The UI only collected a counter name and never exposed service assignment.
- Result: the schema technically allowed a legacy many-link representation, but the product had no usable one-counter-to-one-service flow.
- Pre-fix code:
  - `app/src/main/java/com/cuecall/app/data/local/entity/Entities.kt`
  - `app/src/main/java/com/cuecall/app/domain/model/Models.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/CounterManagementScreen.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/CounterManagementViewModel.kt`

### 5. How printer settings enumerated Bluetooth devices before the fix
- It already used `BluetoothAdapter.bondedDevices`.
- The failure was not discovery logic; it was Android 12+ permission handling.
- On Android 12+, reading bonded devices requires `BLUETOOTH_CONNECT`.
- The screen had no runtime permission request flow, so `bondedDevices` could return empty via `SecurityException` handling.
- Pre-fix code:
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/PrinterSettingsViewModel.kt`

### 6. Whether Android 12+ Bluetooth runtime permissions were implemented correctly before the fix
- No.
- Manifest declared `BLUETOOTH_CONNECT`, but runtime permission request flow was missing.
- The app also did not need active scanning for bonded-printer listing, so `BLUETOOTH_SCAN` was unnecessary noise.

## Root causes

### 1. Root cause of the setup-complete failure
- There was no canonical setup validator.
- Services and counters could be created with a blank `clinicId` because those screens only read DataStore and did not require a clinic row.
- Token generation then failed because it required `settings.clinicId` to be non-blank.
- Result: setup data could appear partially usable while token generation still reported “complete setup”.

### 2. Root cause of missing counter-service association
- Counter persisted a legacy `serviceIdsJson` field, but the UI did not expose service selection at all.
- Counter mode also depended on assigned service settings rather than reading the counter’s linked service.
- Result: counters existed, but there was no usable service linkage path.

### 3. Root cause of printer device not appearing
- Printer settings swallowed `SecurityException` and returned an empty list when `BLUETOOTH_CONNECT` was not granted at runtime on Android 12+.
- The screen also did not clearly surface permission-disabled or Bluetooth-disabled states.
- Result: already paired printers were invisible inside CueCall even though other apps could see them.

## Fixes applied

### Clinic setup and token validation
- Added a single canonical validator:
  - `app/src/main/java/com/cuecall/app/domain/usecase/SetupValidator.kt`
- `GenerateTokenUseCase` now uses that validator and returns specific prerequisite errors:
  - `app/src/main/java/com/cuecall/app/domain/usecase/UseCases.kt`
- Added a real clinic setup screen and save flow:
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/ClinicSetupScreen.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/ClinicSetupViewModel.kt`
- Settings now exposes Clinic Setup:
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/SettingsScreen.kt`
  - `app/src/main/java/com/cuecall/app/navigation/NavGraph.kt`
  - `app/src/main/java/com/cuecall/app/navigation/Screen.kt`
- Existing blank-clinic services/counters are adopted into the saved clinic during setup save:
  - `app/src/main/java/com/cuecall/app/data/local/dao/Daos.kt`
  - `app/src/main/java/com/cuecall/app/data/repository/RepositoryImpls.kt`

### Counter to service linkage
- Counter model is now one counter to one service:
  - `app/src/main/java/com/cuecall/app/domain/model/Models.kt`
  - `app/src/main/java/com/cuecall/app/data/local/entity/Entities.kt`
  - `app/src/main/java/com/cuecall/app/data/repository/Mappers.kt`
- Added Room migration from `serviceIdsJson` to `serviceId`:
  - `app/src/main/java/com/cuecall/app/data/local/AppDatabase.kt`
  - `app/schemas/com.cuecall.app.data.local.AppDatabase/2.json`
- Counter management now supports selecting and editing the linked service, and shows the service name in the list:
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/CounterManagementViewModel.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/CounterManagementScreen.kt`
- Counter runtime flow now resolves service from the linked counter assignment:
  - `app/src/main/java/com/cuecall/app/ui/screens/counter/CounterViewModel.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/counter/CounterScreen.kt`

### Printer listing and permission handling
- Manifest now keeps only the required bonded-device permission path:
  - `app/src/main/AndroidManifest.xml`
- Added runtime `BLUETOOTH_CONNECT` request flow and clearer state messaging:
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/PrinterSettingsScreen.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/PrinterSettingsViewModel.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/BondedPrinterDevicesResolver.kt`
- Printer connection now reports clearer permission/Bluetooth-disabled failures:
  - `app/src/main/java/com/cuecall/app/printer/EscPosPrinterManager.kt`
- Debug builds no longer fake real hardware behavior by forcing the mock printer manager:
  - `app/src/main/java/com/cuecall/app/di/Modules.kt`

## Exact code locations changed

- Setup validation:
  - `app/src/main/java/com/cuecall/app/domain/usecase/SetupValidator.kt`
  - `app/src/main/java/com/cuecall/app/domain/usecase/UseCases.kt`
- Clinic setup UI/navigation:
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/ClinicSetupScreen.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/ClinicSetupViewModel.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/SettingsScreen.kt`
  - `app/src/main/java/com/cuecall/app/navigation/NavGraph.kt`
  - `app/src/main/java/com/cuecall/app/navigation/Screen.kt`
- Counter linkage and migration:
  - `app/src/main/java/com/cuecall/app/domain/model/Models.kt`
  - `app/src/main/java/com/cuecall/app/data/local/entity/Entities.kt`
  - `app/src/main/java/com/cuecall/app/data/local/dao/Daos.kt`
  - `app/src/main/java/com/cuecall/app/data/local/AppDatabase.kt`
  - `app/src/main/java/com/cuecall/app/domain/repository/Repositories.kt`
  - `app/src/main/java/com/cuecall/app/data/repository/RepositoryImpls.kt`
  - `app/src/main/java/com/cuecall/app/data/repository/Mappers.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/CounterManagementViewModel.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/CounterManagementScreen.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/counter/CounterViewModel.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/counter/CounterScreen.kt`
  - `app/schemas/com.cuecall.app.data.local.AppDatabase/2.json`
- Printer fixes:
  - `app/src/main/AndroidManifest.xml`
  - `app/src/main/java/com/cuecall/app/printer/EscPosPrinterManager.kt`
  - `app/src/main/java/com/cuecall/app/di/Modules.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/BondedPrinterDevicesResolver.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/PrinterSettingsViewModel.kt`
  - `app/src/main/java/com/cuecall/app/ui/screens/settings/PrinterSettingsScreen.kt`
- Tests:
  - `app/src/test/java/com/cuecall/app/domain/usecase/GenerateTokenUseCaseTest.kt`
  - `app/src/test/java/com/cuecall/app/domain/usecase/SetupValidatorTest.kt`
  - `app/src/test/java/com/cuecall/app/data/repository/MappersTest.kt`
  - `app/src/test/java/com/cuecall/app/ui/screens/settings/BondedPrinterDevicesResolverTest.kt`
  - `app/src/androidTest/java/com/cuecall/app/data/local/AppDatabaseMigrationTest.kt`
