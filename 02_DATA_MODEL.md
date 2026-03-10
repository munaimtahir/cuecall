# Core Data Model

## Clinic
- id
- name
- logoUrl
- address
- phone
- createdAt
- updatedAt

## Service
- id
- clinicId
- name
- code
- tokenPrefix
- isActive
- sortOrder
- createdAt
- updatedAt

## Counter
- id
- clinicId
- name
- serviceIds[]
- isActive
- createdAt
- updatedAt

## Device
- id
- clinicId
- deviceName
- deviceMode
- assignedServiceId
- assignedCounterId
- printerName
- lastSeenAt

## QueueDay
- id
- clinicId
- businessDate
- isOpen
- resetStrategy
- createdAt
- updatedAt

## Token
- id
- clinicId
- queueDayId
- serviceId
- counterId
- tokenPrefix
- tokenNumber
- displayNumber
- status
- issuedAt
- calledAt
- completedAt
- skippedAt
- recalledAt
- cancelledAt
- notes
- createdByDeviceId
- updatedByDeviceId

## CallEvent
- id
- clinicId
- tokenId
- actionType
- serviceId
- counterId
- createdAt
- createdByDeviceId
- metadata

## AppSettings
- clinicId
- dailyResetEnabled
- defaultStartNumber
- ticketFooterText
- soundEnabled
- displayRefreshMode
- printerPaperWidth
