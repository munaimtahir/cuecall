# Queue Manager Android App — Plain-Language App Description

## Working name
CueCall Android

## What this app does
This is an Android-based queue management system for clinics, labs, OPDs, ultrasound centers, and small service counters.

It has three main functions inside one Android app:
1. Reception mode — generate and print queue tokens
2. Counter/Doctor mode — call next token, recall token, complete token
3. Display mode — show the live queue on an Android device attached to an LCD/TV

The app should work well in a small real-world setup without requiring a heavy server environment.

## Main real-world setup
- One Android phone/tablet at reception
- One Bluetooth thermal mini printer attached to reception device
- One Android device attached to an LCD/TV for queue display
- One or more Android devices for doctor/counter staff
- All devices connected to the same internet/Wi‑Fi network

## Main user roles
### 1. Reception staff
- Select doctor/service
- Generate a token
- Print token on Bluetooth mini thermal printer
- Reprint token if needed
- View current waiting queue

### 2. Doctor / counter staff
- See their own queue
- Call next token
- Recall current token
- Mark token as completed
- Mark token as skipped

### 3. Display screen operator
- Open Display mode
- Select all queues or one selected doctor/service queue
- Show now serving and waiting tokens full screen

### 4. Admin / setup user
- Add clinic name/logo
- Create services/doctors/counters
- Pair Bluetooth printer
- Set token prefix and starting rules
- Set announcement preferences
- Reset queue for next day

## Core workflow
### Reception flow
1. Open Reception mode
2. Select service or doctor
3. Tap “Generate token”
4. App creates token number
5. Token saved to queue
6. Token printed on Bluetooth thermal printer
7. Queue instantly updates on all connected devices

### Counter / doctor flow
1. Open Counter mode
2. Select assigned doctor/counter/service
3. Tap “Call next”
4. Current token becomes active
5. Display screen updates in real time
6. Optional sound / voice plays on display device
7. Staff can recall, skip, or complete token

### Display flow
1. Open Display mode
2. Select queue filter (all queues or one queue)
3. Show full-screen board with:
   - now serving
   - next waiting tokens
   - doctor/service name
   - counter/room
   - clinic branding
4. Auto-refresh in real time

## Token numbering logic
The system should support:
- Numeric sequence only: 001, 002, 003
- Prefix + number: G-001, U-001, L-001
- Separate token series per service/doctor
- Daily reset option
- Manual reset option

Recommended MVP logic:
- Each service has its own prefix
- Each day starts fresh from 001 unless admin disables daily reset

## Printer requirements
The app should support common ESC/POS Bluetooth thermal printers.

### Printed token content
- Clinic name
- Date and time
- Service / doctor name
- Token number
- Optional line: Please wait for your turn
- Optional QR code / short code later

## Display screen layout
The queue display should be simple and readable from a distance.

### Must show
- Current token
- Current doctor/service/counter
- Next 3–8 waiting tokens
- Current date/time
- Clinic name/logo

### Optional later
- Voice announcement
- Urdu/English bilingual text
- Scrolling notice bar

## Suggested MVP feature set
### Must-have
- Android login-free local staff access for MVP
- Reception mode
- Counter mode
- Display mode
- Create token
- Print token over Bluetooth
- Call next token
- Recall token
- Skip token
- Complete token
- Real-time queue sync
- Daily history log
- Daily queue reset
- Service/doctor management
- Printer pairing/settings

### Good but still MVP-safe
- Multi-service queues
- Reprint token
- Search token by number
- Simple analytics: total issued, completed, skipped
- Basic sound alert on display screen

### Later-phase features
- Cloud branch management
- Appointment + walk-in merge
- WhatsApp/SMS alerts
- Online pre-token booking
- Urdu voice announcements
- Web dashboard
- Queue delay estimation
- Patient self check-in kiosk

## Recommended technical direction
### Best MVP stack
- Android native app in Kotlin
- Jetpack Compose UI
- Firebase Firestore for real-time sync
- Room DB for local cache/offline support
- Bluetooth ESC/POS print integration
- Android TV / Android tablet display mode for queue board

## Why this stack
- Fast MVP delivery
- Smooth Android hardware support
- Easy real-time sync
- Easy expansion later
- Good offline/cache path

## Data model (plain language)
### Clinic
Stores clinic profile and branding

### Service
Example: General OPD, Ultrasound, Lab, Consultant Room 1

### Counter
Optional physical place or room name

### Printer
Saved paired printer information

### Queue Day
Represents one active business date

### Token
Each patient queue ticket with number, prefix, status, timestamps, service, and current stage

### Call Event
Stores every call/recall/skip/complete action for audit/history

## Token statuses
- waiting
- called
- recalled
- skipped
- completed
- cancelled

## Live sync behavior
All devices must see queue changes within seconds.

Examples:
- Reception issues token → display updates
- Doctor taps call next → display changes immediately
- Staff skips token → status visible everywhere

## UX expectations
- Very large buttons
- Very low training requirement
- Full-screen display mode
- Quick startup
- Safe for busy front desk users
- Minimal typing

## MVP success criteria
The MVP is successful if a small clinic can do the following in real practice:
1. Pair a Bluetooth printer
2. Generate token in under 2 taps after selecting service
3. Print a readable ticket
4. Call next from doctor/counter device
5. See token update on display within 2–3 seconds
6. Reset next day without confusion

## Commercial packaging idea
This can be sold as:
- one-time install package for small clinics
- monthly subscription with support
- branded deployment for labs / clinics / hospital counters

## MVP boundaries
Do not overbuild version 1.

Avoid in v1:
- complicated appointment system
- billing integration
- patient registration dependency
- web admin panel
- role-heavy enterprise permissions
- multilingual complexity beyond a simple label layer

The goal is a small, stable, installable queue system that solves the token + display + call workflow.
