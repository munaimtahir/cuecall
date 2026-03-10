# System Architecture

## MVP architecture choice
Single Android application with multiple operational modes:
- Reception Mode
- Counter Mode
- Display Mode
- Admin/Settings Mode

## Sync model
Preferred MVP:
- Firebase Authentication (anonymous/device-based or lightweight staff auth)
- Firebase Firestore for live queue state
- Room database for local caching

## Device roles
### Reception device
- token creation
- print commands
- queue overview

### Counter device
- queue actions
- next/recall/skip/complete

### Display device
- full-screen passive real-time board

## High-level components
- presentation layer: Jetpack Compose screens
- domain layer: use cases for queue actions
- data layer: Firestore + Room + printer adapter
- print layer: ESC/POS service
- sync layer: repository with local-first cache strategy

## Recommended package structure
com.example.queueapp
- app/
- core/
- feature_reception/
- feature_counter/
- feature_display/
- feature_settings/
- data/
- domain/
- printer/
- sync/

## Firestore collections
- clinics
- services
- counters
- devices
- queue_days
- tokens
- call_events
- settings

## Offline behavior
- queue list cached in Room
- settings cached locally
- token creation protected against duplicate submission
- queued writes retried when internet resumes

## Real-time update targets
- display refresh under 3 seconds
- queue actions visible on all devices nearly instantly
