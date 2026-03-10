# CueCall — Manual Smoke Test Checklist

Use this checklist for every release candidate. Run on a real Android device connected to the same Wi-Fi as other test devices.

---

## 0. Pre-conditions
- [ ] Firebase project created and `google-services.json` installed
- [ ] App installed on at least 2 devices (one for reception, one for counter/display)
- [ ] Firestore rules allow read/write for anonymous users
- [ ] Bluetooth thermal printer is paired in Android system Bluetooth settings (optional for core flows)

---

## 1. First Launch / Setup
- [ ] App installs without crash on Android 8.0+ (API 26+)
- [ ] Mode selection screen appears on first launch
- [ ] Settings screen opens from mode selection
- [ ] Firebase anonymous sign-in completes silently (no login screen)
- [ ] Room database initializes without crash (no crash on app open)

---

## 2. Setup — Services
- [ ] Service management screen opens
- [ ] Adding a service with name + prefix + code saves and appears in list
- [ ] Token prefix is forced to uppercase and max 3 chars
- [ ] Inactive toggle hides service from reception screen
- [ ] Deleting a service removes it from the list
- [ ] 3 demo services appear on first launch (General OPD G, Ultrasound U, Lab L)

---

## 3. Setup — Counters
- [ ] Counter management screen opens
- [ ] Adding a counter with a name saves and appears in list
- [ ] Deleting a counter removes it

---

## 4. Printer Settings
- [ ] Printer settings screen opens
- [ ] Bluetooth paired devices from Android system appear in list
- [ ] Selecting a device saves it as the paired printer
- [ ] Paper width selection (58mm / 80mm) saves correctly
- [ ] Connect button triggers connection attempt
- [ ] Connection status shows correct state

---

## 5. Reception Flow
- [ ] Reception screen opens from mode selection
- [ ] Active services appear as selectable cards
- [ ] Selecting a service highlights it
- [ ] Tapping "Generate Token" with a service selected:
  - [ ] Token number is generated (G-001 on first token)
  - [ ] Token appears on screen with display number
  - [ ] Token is saved to Firestore
- [ ] Second token increments: G-002
- [ ] Token from different service has its own sequence: U-001
- [ ] Tapping "Generate Token" without network shows error (not crash)
- [ ] "Reprint Last Token" button appears after first token
- [ ] Reprint sends to mock printer (logs to Logcat in debug)
- [ ] "Reprint Last Token" sends ticket with correct fields

---

## 6. Counter Flow
- [ ] Counter screen opens from mode selection
- [ ] Waiting tokens list shows tokens generated at reception
- [ ] "Call Next" calls the lowest-number waiting token
- [ ] Called token appears in "Now Serving" card
- [ ] Called token disappears from the waiting list
- [ ] "Recall" button sets token to RECALLED
- [ ] "Skip" button sets token to SKIPPED; token removed from active card
- [ ] "Done" button sets token to COMPLETED; token removed from active card
- [ ] "Call Next" shows "No tokens waiting" when queue is empty
- [ ] Calling next on empty queue does not crash

---

## 7. Display Board Flow
- [ ] Display mode opens full-screen with dark background
- [ ] Clinic name appears in header (or "CueCall" if not configured)
- [ ] Current time shown and updates each minute
- [ ] After a token is called on counter device, display updates within 3 seconds
- [ ] "Now Serving" shows the currently called token number in large format
- [ ] Waiting tokens appear below (up to 8)
- [ ] Back button shows "Exit Display Mode?" confirmation dialog
- [ ] Pressing "Stay" dismisses dialog and stays in display mode
- [ ] Pressing "Exit" returns to mode selection

---

## 8. Daily History
- [ ] Daily history screen shows today's date
- [ ] Summary row shows correct Issued / Done / Skipped / Waiting counts
- [ ] Token list shows all tokens for today sorted newest-first
- [ ] Status color: green for COMPLETED, red for SKIPPED, blue for CALLED/RECALLED

---

## 9. Real-Time Sync
- [ ] Token generated on Device A appears on Device B display within 3 seconds
- [ ] Token called on Device B counter updates display on Device C within 3 seconds
- [ ] Token completed on counter disappears from display waiting list
- [ ] Skipped token disappears from display waiting list

---

## 10. Offline/Resilience
- [ ] Turning off Wi-Fi then generating a token shows "no network connection" error
- [ ] Re-enabling Wi-Fi allows token generation
- [ ] With Wi-Fi off, counter actions (call/skip/complete) succeed locally and sync when reconnected
- [ ] Display shows stale data (Firestore cache) when offline with no crash
- [ ] App re-open after force-close restores queue state from Room cache

---

## 11. Data Integrity
- [ ] Two devices generating tokens simultaneously do not produce duplicate numbers for the same service/day
  - Test: Generate rapidly from 2 devices at same time, verify no duplicate displayNumbers
- [ ] Tokens from service A do not affect sequence of service B
- [ ] After daily reset (midnight), new tokens start from G-001

---

## 12. Edge Cases
- [ ] App handles 0 services gracefully (reception shows empty state)
- [ ] App handles 0 counters gracefully (display still shows token number)
- [ ] Very long clinic name does not break display layout
- [ ] Token number 999 displays as G-999 (no truncation)

---

## Known Limitations (v1 MVP)

| Limitation | Notes |
|---|---|
| Token generation blocked offline | By design — requires Firestore atomic sequence |
| No voice announcements | TTS deferred to v2 |
| No Firebase Auth UI | Anonymous auth only; no login screen |
| Printer requires real hardware for release builds | Debug builds use MockPrinterManager |
| No multi-branch support | Single clinic per installation |
| No appointment integration | Queue-only, walk-in tokens |
