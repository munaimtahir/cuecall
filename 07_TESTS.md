# QA / Test Checklist

## Setup tests
- app installs successfully on Android device
- Firebase config loads correctly
- Room database initializes without crash
- printer pairing screen opens without crash

## Reception tests
- service list loads
- token generates successfully
- token sequence increments correctly
- token display number formats correctly
- token prints successfully when printer connected
- reprint works

## Counter tests
- next token call works
- recall works
- skip works
- complete works
- current token updates correctly

## Display tests
- display mode opens full screen
- current token updates live
- waiting list updates live
- service filter works
- date/time visible

## Sync tests
- token generated on reception appears on display device
- token called on counter appears on display device
- completed token removed from waiting list correctly

## Resilience tests
- printer disconnected during print
- internet reconnect after temporary loss
- app reopen restores current queue state

## Data integrity tests
- no duplicate token numbers for same service/day
- status transitions remain valid
