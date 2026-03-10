# CueCall — Android Queue Manager

An Android-native clinic queue management system with Bluetooth token printing and live display board.

## Quick Start

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- A Firebase project (Firestore + Anonymous Auth enabled)
- Android device or emulator (API 26+)

### 1. Clone and configure Firebase

```bash
git clone <repo-url>
cd cuecall
```

Replace `app/google-services.json` with your real Firebase `google-services.json` file.  
See [Firebase console](https://console.firebase.google.com) → Project Settings → Your apps → Android.

Enable in Firebase console:
- **Firestore Database** (start in test mode for development)
- **Authentication → Sign-in method → Anonymous** (enable)

### 2. Build

```bash
./gradlew assembleDebug
```

### 3. Install on device

```bash
./gradlew installDebug
```

Or open in Android Studio and click **Run**.

### 4. First launch setup

1. Open app → tap **Settings**
2. Note: clinic ID is auto-generated. On first launch, 3 demo services are created (General OPD, Ultrasound, Lab).
3. Add counters if needed.
4. Pair a Bluetooth printer in Android Bluetooth settings, then configure in **Settings → Printer Settings**.
5. Return to mode selection and choose your device role.

---

## Architecture

```
com.cuecall.app/
├── data/
│   ├── local/          — Room DB (AppDatabase, entities, DAOs)
│   ├── remote/         — Firestore DTOs and FirestoreTokenSource
│   └── repository/     — Repository implementations + mappers
├── domain/
│   ├── model/          — Pure Kotlin domain models
│   ├── repository/     — Repository interfaces
│   └── usecase/        — Business logic use cases
├── ui/
│   ├── components/     — Reusable Compose components
│   ├── screens/        — All screens + ViewModels
│   └── theme/          — Material 3 theme
├── printer/            — PrinterManager interface + Mock + EscPos stub
├── sync/               — TokenSyncManager (Firestore → Room bridge)
├── navigation/         — NavGraph + Screen routes
└── di/                 — Hilt DI modules
```

**Stack:** Kotlin · Jetpack Compose · Material 3 · Hilt · Room · Firebase Firestore · Navigation Compose · Coroutines/Flow

---

## Running Tests

```bash
# Unit tests
./gradlew :app:test

# Specific test class
./gradlew :app:testDebugUnitTest --tests "com.cuecall.app.domain.usecase.GenerateTokenUseCaseTest"
```

---

## Firestore Schema

```
clinics/{clinicId}/
├── tokens/{tokenId}
├── queue_days/{queueDayId}/
│   └── sequences/{serviceId}      ← atomic nextNumber counter
├── services/{serviceId}
├── counters/{counterId}
└── devices/{deviceId}
```

QueueDay ID format: `{clinicId}_{yyyy-MM-dd}` (deterministic, collision-safe across devices).

---

## Offline Behavior

| Action | Offline Behavior |
|---|---|
| Generate token | **Blocked** — requires Firestore transaction |
| Call / Recall / Skip / Complete | Best-effort via Firestore offline persistence |
| Display board | Shows last-known state from Firestore SDK cache |

---

## Printer Integration

Debug builds use `MockPrinterManager` (logs to Logcat, always succeeds).

Release builds use `EscPosPrinterManager` which implements raw ESC/POS over RFCOMM Bluetooth. The integration point for vendor-specific SDKs is clearly marked with `TODO` in:
```
app/src/main/java/com/cuecall/app/printer/EscPosPrinterManager.kt
```

---

## Deployment Checklist

- [ ] Replace `app/google-services.json` with production Firebase config
- [ ] Set Firestore security rules (restrict reads/writes to authenticated anonymous users)
- [ ] Test Bluetooth printer pairing on target device
- [ ] Run full [Smoke Test Checklist](docs/SMOKE_TEST_CHECKLIST.md)
- [ ] Build release APK: `./gradlew assembleRelease`

---

## Docs

| File | Purpose |
|---|---|
| `docs/IMPLEMENTATION_REVIEW.md` | Architecture decisions, assumptions, gaps |
| `docs/PRODUCT_DECISIONS.md` | Locked product decisions for MVP |
| `docs/SMOKE_TEST_CHECKLIST.md` | Manual QA checklist |

---

## Planning Pack (original)

- 00_APP_DESCRIPTION.md — plain-language product spec
- 01_SYSTEM_ARCHITECTURE.md — architecture overview
- 02_DATA_MODEL.md — data model reference
- 03_FEATURE_SCOPE.md — MVP feature list
- 04_AI_AGENT_MASTER_PROMPT.md — agent build prompt
- 05_CURSOR_PROMPT.md — Cursor IDE prompt
- 06_CODEX_PROMPT.md — validation checklist prompt
- 07_TESTS.md — QA test intent
- 08_API_CONTRACT_NOTES.md — API notes
- 09_REPO_SCAFFOLD.txt — initial scaffold reference

