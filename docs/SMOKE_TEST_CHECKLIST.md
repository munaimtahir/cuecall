# CueCall — Manual Smoke Test Checklist

Use this checklist for every release candidate. Run on a real Android device connected to the same Wi-Fi as other test devices.

---

## 0. Pre-conditions
- [ ] Firebase project created and `google-services.json` installed
- [ ] App installed on at least 2 devices (one for reception, one for counter/display)
- [ ] Firestore rules allow read/write for anonymous users
- [ ] Bluetooth thermal printer is paired in Android system Bluetooth settings

---

## 1. Initial Clinic Setup
- [ ] App installs without crash on Android 8.0+ (API 26+)
- [ ] Mode selection screen appears on first launch
- [ ] Settings opens from mode selection
- [ ] `Settings -> Clinic Setup` opens
- [ ] Saving clinic name succeeds
- [ ] Returning to `Clinic Setup` shows the saved clinic values
- [ ] After save, service/counter screens no longer behave as if setup is incomplete

## 2. Service Setup
- [ ] Service management screen opens
- [ ] Adding a service with name + prefix + code saves and appears in list
- [ ] Token prefix is forced to uppercase and max 3 chars
- [ ] If no clinic is configured, service add shows a precise setup error

## 3. Counter Setup
- [ ] Counter management screen opens
- [ ] Creating a counter requires selecting a service
- [ ] Added counter shows both counter name and linked service name
- [ ] Editing a counter allows changing the linked service
- [ ] Existing counters without a service show safely as unassigned until edited

## 4. Token Generation
- [ ] Reception screen opens
- [ ] Active services appear as selectable cards
- [ ] Generating a token after clinic setup succeeds
- [ ] First token for a service starts at `PREFIX-001`
- [ ] Second token increments correctly
- [ ] Token generation no longer shows a generic “complete setup” error when clinic + service are valid
- [ ] If setup is invalid, the error names the missing prerequisite precisely

## 5. Counter Flow
- [ ] Counter screen uses the linked counter service when a counter is assigned to the device
- [ ] Waiting tokens list only shows tokens for that service
- [ ] `Call Next` calls the lowest-number waiting token
- [ ] `Recall`, `Skip`, and `Done` work without crash
- [ ] If the linked service is inactive or missing, the counter screen shows a precise error

## 6. Printer Permissions and Bonded Devices
- [ ] `Settings -> Printer Settings` opens
- [ ] On Android 12+ the screen requests Bluetooth permission
- [ ] If permission is denied, the screen explains that Bluetooth permission is required
- [ ] If Bluetooth is disabled, the screen explains that clearly
- [ ] Already paired Bluetooth printers appear without starting discovery
- [ ] Each printer row shows device name and MAC address

## 7. Printer Selection and Persistence
- [ ] Selecting a bonded printer marks it as selected
- [ ] Leaving and reopening printer settings preserves the selected printer
- [ ] `Connect` attempts a real connection
- [ ] Connection success or failure is shown honestly

## 8. Printing Outcome
- [ ] After token generation, printing either succeeds on the selected printer or shows a clean connection/permission error
- [ ] No mock-success path is shown for real hardware testing

## Known Limitations (MVP)
- Token generation still requires network because Firestore sequence reservation is authoritative
- Counter-to-device assignment still depends on existing device settings; this change only fixes counter-to-service linkage
- Printer success still depends on target hardware supporting generic ESC/POS over RFCOMM
