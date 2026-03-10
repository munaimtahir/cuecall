# Product Decisions — CueCall Android MVP

These decisions are locked for MVP implementation. Changes after scaffold require explicit revision.

---

## 1. Definition of Service

A **Service** is a named queue category that patients join. It maps to a doctor, clinic department, or service type.

Examples: `General OPD`, `Ultrasound`, `Lab`, `Dr. Ahmed`, `Consultant Room 1`

- Every token belongs to exactly one service
- Every service has its own token prefix (e.g., `G`, `U`, `L`)
- Every service maintains its own token sequence per queue day
- Services are created by admin; not by reception staff during normal operation
- Services have `isActive` flag — inactive services are hidden from reception

**Doctor is NOT a separate entity in MVP.** A doctor's queue is represented as a Service with the doctor's name.

---

## 2. Definition of Counter

A **Counter** is an optional physical window or room label (e.g., `Window 1`, `Room 3`).

- Counters are informational/display metadata in MVP
- A counter can be associated with multiple services (stored as comma-joined IDs in Room)
- Counters are shown on the display board alongside the token
- A device assigned to Counter mode sets its `assignedCounterId` in device settings
- Counter assignment is per-device, not per-session

**Counter is not required** to call tokens. A counter device can operate without a counter label (it just won't show a counter name on the display board).

---

## 3. Doctor as an Entity

**Doctor is NOT a first-class entity in MVP.**

Rationale: The planning pack treats doctor queues identically to service queues. Adding a Doctor entity would require linking doctors to services, managing doctor schedules, and handling doctor-vs-service display logic — all unnecessary for MVP.

If a clinic wants `Dr. Ahmed`'s queue, they create a Service named `Dr. Ahmed` with prefix `A`.

Post-MVP: A `Doctor` entity can be added and linked to a Service without breaking existing data.

---

## 4. Device Roles and Permissions

### Device Modes
| Mode | Value |
|---|---|
| Reception | `RECEPTION` |
| Counter | `COUNTER` |
| Display | `DISPLAY` |
| Unassigned | `UNASSIGNED` |

### Rules
- Any device can switch modes from the mode selection screen
- Mode selection is accessible at app start; switching requires navigating back to home
- Settings/Admin screen is accessible from any mode (with optional PIN)
- Display mode locks the screen (exit requires back × 2 or long-press)
- No role-based access control beyond the optional admin PIN in MVP
- Device registers itself in Firestore `devices` collection on first run with a generated device UUID
- `assignedServiceId` and `assignedCounterId` are set per device in Settings

---

## 5. Token Numbering Strategy

### Sequence
- Token numbers are **per service per queue day**
- Sequence starts at **1** each day (when daily reset is enabled)
- Sequence is stored as an atomic counter in Firestore under:
  `clinics/{clinicId}/queue_days/{queueDayId}/sequences/{serviceId}`
- The counter field name is `nextNumber`
- Token generation uses `Firestore.runTransaction` to read and increment `nextNumber` atomically
- This prevents duplicate tokens even if two reception devices act simultaneously

### Display Format
- `displayNumber = "{tokenPrefix}-{tokenNumber.toString().padStart(3, '0')}"`
- Example: service prefix `G`, number 5 → `G-005`
- If prefix is empty, displayNumber = `005` (plain zero-padded)

### Constraints
- Max token number per service per day: 999 (3-digit display). This is a soft limit — no hard enforcement in MVP.
- Token prefix can be 1–3 uppercase characters. Validated in service creation UI.

---

## 6. Queue Day Reset Rules

### Automatic daily reset
- Enabled by default (`dailyResetEnabled = true` in AppSettings)
- A new QueueDay record is created automatically when the first token of a new business date is generated
- Business date = device local date at the time of token generation (format: `yyyy-MM-dd`)
- QueueDay ID = `{clinicId}_{businessDate}` (deterministic, collision-safe)
- If a QueueDay for today already exists, it is reused; no duplicate creation

### Manual reset
- Admin can trigger a manual queue reset from Settings
- Manual reset creates a new QueueDay with a forced new ID (appended with timestamp suffix in MVP if same day)
- Existing tokens are preserved; their queueDayId remains unchanged
- After reset, sequence starts from 1 for the new QueueDay

### Behavior when reset is disabled
- `dailyResetEnabled = false` → sequence continues across calendar days indefinitely
- This is a clinic-level setting stored in AppSettings

---

## 7. Offline Behavior Rules

| Action | Offline Behavior |
|---|---|
| **Generate token** | **BLOCKED**. Token generation requires a Firestore transaction for safe sequence numbering. If device is offline, show clear error: "Cannot generate token: no network connection." |
| **Call next token** | **BEST-EFFORT**. If Firestore write fails, the action is queued locally (Firestore offline persistence) and will sync when reconnected. Counter screen shows stale data from Room cache. |
| **Recall / Skip / Complete** | Same as call next — best-effort with Firestore offline persistence |
| **Display board** | Shows last-known state from Firestore cache (Firestore SDK local cache). May be stale. A "Syncing…" indicator shown if listener not connected. |
| **Settings reads** | Served from Room local cache. |
| **Settings writes** | Blocked if Firestore is required; shown with error. Local-only settings changes (deviceMode, etc.) persist to Room immediately. |

**Firestore offline persistence is ENABLED** in the SDK initialization. This covers counter actions and display reads automatically.

Token generation is the only action that explicitly checks connectivity before attempting.

---

## 8. Display Board Behavior

- Full-screen mode; navigation bar hidden (immersive mode)
- Shows:
  - Clinic name (top header)
  - Current date and time (top right, refreshes every minute)
  - "Now Serving" section: token displayNumber + service name + counter name (if set)
  - "Waiting" section: next 5 tokens (displayNumber + service name)
  - Footer: configurable text from AppSettings
- Data source: Firestore snapshot listener on `tokens` collection filtered by:
  - `clinicId = {current clinicId}`
  - `queueDayId = {today's QueueDay ID}`
  - `status in [called, waiting]`
- Updates immediately on Firestore change (under 3 seconds target)
- Sound: a simple beep plays when a new "called" token appears (uses Android `ToneGenerator`, no external library)
- **Exit guard**: back button press while in Display mode shows an "Exit Display?" dialog. Double back or dialog confirm exits.
- Service filter: user can select "All Services" or a specific service before entering Display mode. Stored in local ViewModel state.

---

## 9. Printer Abstraction Assumptions

- Interface: `PrinterManager` (in `com.cuecall.app.printer`)
- Methods: `connect(address: String)`, `disconnect()`, `printTicket(ticket: TokenTicket): Result<Unit>`, `isConnected(): Boolean`
- `TokenTicket` data class contains: clinicName, serviceName, tokenDisplayNumber, issuedAt, footerText
- **`MockPrinterManager`**: logs the ticket to Logcat. Used in debug builds and when no printer is paired. Simulates success immediately.
- **`EscPosPrinterManager`**: real stub — implements the interface, contains `TODO("Wire real ESC/POS SDK here")` in the print method. All connection and byte-formatting scaffolding is present. The exact SDK call site is clearly isolated.
- ESC/POS byte sequences for text formatting are partially implemented (line feed, text alignment, bold, cut). These are vendor-agnostic standard ESC/POS commands.
- Printer pairing uses Android `BluetoothAdapter` to discover and list paired devices. The user selects from this list in Settings. Selected printer MAC address is stored in AppSettings.
- **58mm paper width** is the default. This is configurable in AppSettings (`printerPaperWidth`).
- The printer layer does NOT depend on the queue domain. It receives a `TokenTicket` and prints it, period.
