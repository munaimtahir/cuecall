# Contract Notes

This MVP is Firestore-backed and does not require a separate backend API initially.

## Important contract behaviors
- token creation must be atomic per service per queue day
- status transitions must be controlled by domain use cases
- display clients are read-mostly
- call events should append to an audit collection/log

## Recommended future backend path
If later upgraded to backend API:
- FastAPI or Django/NestJS can sit behind the Android app
- Firestore can be replaced or mirrored
- endpoints would include services, tokens, queue actions, settings, history
