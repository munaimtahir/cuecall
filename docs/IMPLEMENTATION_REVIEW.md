# Implementation Review — CueCall Android MVP

## 1. Product Understanding

CueCall is an Android-native clinic queue management system with three operational modes in a single app:

- **Reception Mode** — generate and print numbered queue tokens for patients
- **Counter/Doctor Mode** — call, recall, skip, or complete tokens at a service window
- **Display Mode** — passive full-screen board on a tablet/TV showing now-serving and waiting tokens

A typical clinic setup:
- 1 Android phone/tablet at reception with a paired Bluetooth ESC/POS thermal printer
- 1 Android device connected to a TV/LCD running Display mode
- 1+ Android phones/tablets for doctor or counter staff

Real-time sync is via Firebase Firestore. Room DB provides local cache for resilience.

---

## 2. Final MVP Scope

### In MVP
- Mode selection on startup (Reception / Counter / Display / Settings)
- Reception: service selection, token generation, print, reprint, waiting queue summary
- Counter: assigned queue view, call next, recall, skip, complete
- Display: full-screen board, now serving, waiting list, live Firestore updates
- Settings/Admin: clinic profile, service CRUD, counter CRUD, printer pairing, token prefix config, daily reset
- Multi-service queues with separate token sequences (e.g., G-001, U-001)
- Daily queue reset (automatic by business date, manual override)
- Basic daily history/logs screen
- Bluetooth printer abstraction (ESC/POS stub + mock)
- Firestore + Room local-first sync

### Explicitly Out of MVP
- Appointments / scheduling
- Billing / payment
- Patient registration
- WhatsApp / SMS
- Web admin panel
- Multi-branch enterprise admin
- Advanced analytics dashboards
- Multilingual voice announcements
- Cloud Functions
- User authentication (Firebase Auth not required for MVP; device-based identity)

---

## 3. Assumptions

1. **Single clinic per installation** — the app is deployed for one clinic at a time. No multi-tenant in MVP.
2. **No user login** — device identity is sufficient. A device registers itself on first run.
3. **Package name**: `com.cuecall.app` (production-appropriate)
4. **Min SDK**: 26 (Android 8.0) — covers broad real-world device base
5. **Target SDK**: 34
6. **Token numbers** are per-service per-queue-day, starting from 1, padded to 3 digits (001, 002…)
7. **displayNumber format**: `{prefix}-{paddedNumber}` e.g. `G-001`
8. **Firestore transaction** is used for atomic sequence increment — safe under concurrent reception devices
9. **Doctor is not a first-class entity** — services represent doctor queues (e.g., "Dr. Ahmed", "Ultrasound")
10. **Counter is optional** — a service can exist without a counter assignment; counter is a physical room/window label
11. **Daily reset** is enabled by default, keyed on business date (device local date at token creation time)
12. **Offline behavior**: token generation is **blocked offline** (requires Firestore transaction). Counter actions and display work from Room cache if Firestore is temporarily unreachable but queue state may be stale.
13. **Printer SDK**: No specific vendor SDK is bundled. The abstraction uses a generic ESC/POS byte-level interface. Real hardware integration point is clearly isolated.
14. **Anonymous Firestore auth** — Firebase anonymous sign-in is used to satisfy Firestore security rules without requiring user accounts.

---

## 4. Gaps and Ambiguities Found

| # | Gap / Ambiguity | Decision |
|---|---|---|
| 1 | No Firebase project config (`google-services.json`) available | Placeholder config added; real file required before building |
| 2 | No specific Bluetooth printer SDK mentioned | ESC/POS abstraction with stub; real SDK slot isolated in `printer/` |
| 3 | `Counter.serviceIds[]` — many-to-many or one-to-many? | MVP: a counter serves one service at a time (assignedServiceId on Device); Counter stores comma-joined serviceIds as a text field in Room |
| 4 | How is "assigned counter/service" determined per device? | Device registers itself and stores deviceMode + assignedServiceId + assignedCounterId in AppSettings locally and in Firestore `devices` collection |
| 5 | QueueDay scoping — by clinicId + date? | Yes. QueueDay.id = `{clinicId}_{businessDate}` for deterministic keying |
| 6 | QueueSequence entity — where does it live? | Firestore document: `clinics/{cid}/queue_days/{qdId}/sequences/{serviceId}` — atomic counter field |
| 7 | Cancelled vs Skipped distinction | Both kept; cancelled = admin removes token; skipped = counter staff skips (patient absent) |
| 8 | Voice announcement on display | Deferred from MVP. Sound flag present in AppSettings but TTS not wired. |
| 9 | PIN protection for Settings | A simple 4-digit PIN field in AppSettings; enforced on Settings entry. Blank PIN = no protection. |
| 10 | Display filter mode (all vs. single service) | Display stores preference locally; default = show all services |

---

## 5. Technical Decisions

| Decision | Choice |
|---|---|
| Architecture | MVVM + Repository pattern + Use Cases (Clean-ish, no strict domain layer separation required for MVP) |
| UI framework | Jetpack Compose with Material 3 |
| DI | Hilt |
| Local DB | Room (single database, all entities) |
| Remote sync | Firestore with snapshot listeners; no Cloud Functions |
| Token sequence safety | Firestore `runTransaction` on sequence document |
| Concurrency | Kotlin Coroutines + Flow throughout |
| Navigation | Navigation Compose (single NavHost) |
| Printer | Interface `PrinterManager` + `MockPrinterManager` + `EscPosPrinterManager` stub |
| Theme | Material 3 with clinic-friendly large touch targets |
| Auth | Firebase Anonymous Auth (silent, automatic) |
| State management | `UiState` sealed class per ViewModel |
| Error surfacing | Snackbar via `SnackbarHostState` |

---

## 6. Implementation Order

1. **Docs** — this file + PRODUCT_DECISIONS.md
2. **Project scaffold** — Gradle, AndroidManifest, build config
3. **Theme + common UI** — Material 3 theme, reusable components
4. **Domain models** — pure Kotlin data classes
5. **Room entities + DAOs + Database** — local persistence layer
6. **Firestore DTOs + remote sources** — remote data layer
7. **Repository implementations** — merge local + remote
8. **Use cases** — business logic, token state machine
9. **Hilt DI modules** — wire everything
10. **Navigation** — NavGraph, routes
11. **Screens** — ModeSelection → Reception → Counter → Display → Settings
12. **Printer abstraction** — interface + mock + stub
13. **Tests** — unit tests for use cases and token logic
14. **Smoke test checklist**
15. **README** — build/run instructions
