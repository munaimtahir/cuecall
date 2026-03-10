You are building an Android-first clinic queue management application.

# Project Goal
Build a production-ready MVP Android application that manages queue tokens for clinics/labs/OPDs. The app must support:
1. Reception token generation
2. Bluetooth thermal token printing
3. Counter/doctor queue calling workflow
4. Live display screen mode on Android-connected LCD/TV
5. Real-time queue sync across Android devices

# Product shape
Single Android app with multiple modes:
- Reception Mode
- Counter Mode
- Display Mode
- Settings/Admin Mode

# Required stack
- Kotlin
- Android Studio project
- Jetpack Compose
- MVVM/Clean Architecture
- Firebase Firestore for real-time sync
- Room for local cache/offline state
- ESC/POS Bluetooth thermal printer integration
- Hilt for DI
- Coroutines + Flow

# UX expectations
- very large buttons
- front-desk friendly
- minimal typing
- quick actions
- display mode readable from distance
- clean, modern, simple UI

# Functional requirements
## Reception Mode
- select service/doctor
- generate token
- token numbering based on service prefix + sequence
- save token to active queue day
- print token to Bluetooth printer
- reprint token
- see waiting queue summary

## Counter Mode
- choose assigned queue/service
- call next token
- recall current token
- skip token
- complete token
- current active token visible
- waiting list visible

## Display Mode
- full-screen queue board
- show now serving
- show next waiting tokens
- show clinic name/date/time
- optionally filter by service
- auto-update live
- optional simple beep when token changes

## Settings Mode
- clinic profile
- service management
- counter management
- printer pairing
- token prefix setup
- daily reset configuration

# Data model requirements
Implement entities for:
- Clinic
- Service
- Counter
- Device
- QueueDay
- Token
- CallEvent
- AppSettings

# Token logic rules
- token numbers must be deterministic
- support separate sequence per service per business day
- displayNumber example: G-001
- prevent duplicate sequence generation under concurrent usage
- daily reset enabled by default

# Sync requirements
- real-time updates across devices via Firestore
- Room cache for offline display and temporary resilience
- repository layer must merge remote + local state safely

# Printing requirements
- support ESC/POS Bluetooth thermal printers
- 58mm format initially
- print clinic name, token number, service, date/time, footer note
- printer service should be modular and replaceable

# Code quality requirements
- clean architecture
- repository pattern
- use cases for main actions
- testable business logic
- no hardcoded strings in core logic
- meaningful naming
- no placeholder code or pseudocode in final deliverable

# Deliverables required
1. Full Android project scaffold
2. Working Compose screens
3. Firestore schema assumptions documented
4. Room schema
5. Bluetooth printer integration module
6. Sample seed/demo setup for 3 services
7. README with build/run instructions
8. QA checklist
9. Known limitations note

# Build phases
## Phase 1 — Foundation
- create project scaffold
- configure DI/navigation/theme
- define data models and repositories
- configure Room
- configure Firebase layer

## Phase 2 — Reception workflow
- service selection
- token generation
- queue list
- print integration
- reprint token

## Phase 3 — Counter workflow
- call next
- recall
- skip
- complete
- active token state

## Phase 4 — Display workflow
- full-screen board
- live updates
- service filter
- visual polish

## Phase 5 — Settings/Admin
- clinic setup
- service CRUD
- printer pairing
- reset flow

## Phase 6 — Hardening
- concurrency guard for token generation
- printer failure handling
- offline cache behavior
- test coverage on core flows

# Acceptance criteria
- token can be generated and printed from reception device
- queue state syncs to display and counter devices
- call next updates display correctly
- skip/complete/recall work consistently
- app is installable and usable in a small clinic workflow

# Constraints
- do not build web panel in MVP
- do not add billing/appointments
- do not over-engineer authentication
- prioritize reliable clinic workflow over fancy features

# Output format expected from agent
Return work as:
1. Architecture summary
2. Folder tree
3. Stepwise implementation
4. Important code files
5. Setup instructions
6. Test checklist
7. Remaining TODOs
