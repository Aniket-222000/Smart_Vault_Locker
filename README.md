
# API Documentation with Examples

This document provides a detailed overview of the API endpoints for managing locker access, customer operations, and administrative tasks. Each endpoint includes an example request and response to help you understand its usage.

---

## Locker Access Endpoints (under `/api/locker`)

These endpoints handle operations related to locker access, such as initiating access requests, verifying OTPs, securing lockers, and exiting lockers.

---

### 1. **Initiate Access Request**

**POST** `/api/locker/request`
Initiates an access request using ATM PIN and biometric data. Returns a `requestId` for further operations.

#### Example Request:

```json
POST /api/locker/request
Content-Type: application/json

{
  "atmPin": "1234",
  "biometricData": "fingerprint-sample-data"
}
```


#### Example Response:

```json
{
  "requestId": "REQ12345",
  "message": "Access request initiated successfully"
}
```

---

### 2. **Verify OTP**

**POST** `/api/locker/request/{requestId}/verify`
Verifies the OTP associated with the locker access request.

#### Example Request:

```json
POST /api/locker/request/REQ12345/verify
Content-Type: application/json

{
  "otp": "987654"
}
```


#### Example Response:

```json
{
  "message": "OTP verified successfully"
}
```

---

### 3. **Secure Locker**

**POST** `/api/locker/{lockerNumber}/secure`
Simulates securing the locker with a master key.

#### Example Request:

```json
POST /api/locker/101/secure
```


#### Example Response:

```json
{
  "message": "Locker secured successfully"
}
```

---

### 4. **Exit Locker**

**POST** `/api/locker/{lockerNumber}/exit`
Simulates exiting and releasing the locker.

#### Example Request:

```json
POST /api/locker/101/exit
```


#### Example Response:

```json
{
  "message": "Locker exited and released successfully"
}
```

---

## Customer Endpoints (under `/api/customer`)

These endpoints allow customers to manage their profiles, view billing history, make payments, and submit feedback.

---

### 5. **Retrieve Customer Profile**

**GET** `/api/customer/profile`
Retrieves the customer’s profile details.

#### Example Request:

```http
GET /api/customer/profile
Authorization: Bearer &lt;access_token&gt;
```


#### Example Response:

```json
{
  "customerId": "CUST001",
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+1234567890",
  "address": "123 Main Street, Cityville"
}
```

---

### 6. **Update Customer Profile**

**PUT** `/api/customer/profile`
Updates the customer’s profile details.

#### Example Request:

```json
PUT /api/customer/profile
Content-Type: application/json

{
  "name": "John Doe Updated",
  "email": "john.updated@example.com",
  "phone": "+0987654321",
  "address": "456 Another Street, Townsville"
}
```


#### Example Response:

```json
{
  "message": "Profile updated successfully"
}
```

---

### 7. **Retrieve Billing History**

**GET** `/api/customer/billing`
Retrieves the customer’s billing history.

#### Example Request:

```http
GET /api/customer/billing
Authorization: Bearer &lt;access_token&gt;
```


#### Example Response:

```json
[
  {
    "transactionId": "TXN001",
    "amount": 100.50,
    "date": "2025-04-01",
    "status": "Paid"
  },
  {
    "transactionId": "TXN002",
    "amount": 200.75,
    "date": "2025-03-15",
    "status": "Pending"
  }
]
```

---

### 8. **Simulate Payment**

**POST** `/api/customer/billing/pay`
Simulates a payment for a billing transaction.

#### Example Request:

```json
POST /api/customer/billing/pay
Content-Type: application/json

{
  "transactionId": "TXN002",
  "paymentMethod": "Credit Card"
}
```


#### Example Response:

```json
{
  "message": "Payment processed successfully"
}
```

---

### 9. **Submit Feedback or Complaint**

**POST** `/api/customer/feedback`
Submits feedback or complaints from the customer.

#### Example Request:

```json
POST /api/customer/feedback
Content-Type: application/json

{
  "feedbackType": "Complaint",
  "message": "The locker system was not working properly."
}
```


#### Example Response:

```json
{
  "message": "Feedback submitted successfully"
}
```

---

## Admin Endpoints (under `/api/admin`)

These endpoints provide administrative functionalities such as viewing feedback, generating reports, and managing lockers.

---

### 10. **List All Feedback**

**GET** `/api/admin/feedback`
Lists all feedback submitted by customers.

#### Example Request:

```http
GET /api/admin/feedback
Authorization: Bearer &lt;admin_access_token&gt;
```


#### Example Response:

```json
[
  {
    "feedbackId": "FB001",
    "customerId": "CUST001",
    "feedbackType": "Complaint",
    "message": "The locker system was not working properly.",
    ...
  },
  {
    ...
  }
]
```

---

### 11. **Usage Statistics Report**

**GET** `/api/admin/reports/usage`
Provides usage statistics for the lockers.

#### Example Request:

```http
GET /api/admin/reports/usage
Authorization: Bearer &lt;admin_access_token&gt;
```


#### Example Response:

```json
{
  "totalLockersUsedToday": 50,
  ...
}
```

---

### 12. **Revenue Report**

**GET** `/api/admin/reports/revenue`
Generates a revenue report for the system.

#### Example Request:

```http
GET /api/admin/reports/revenue
Authorization: Bearer &lt;admin_access_token&gt;
```


#### Example Response:

```json
{
  ...
}
```

---

### 13. **List All Lockers and Their Statuses**

**GET** `/api/admin/lockers`
Lists all lockers along with their current statuses (e.g., available, in use).

#### Example Request:

```http
GET /api/admin/lockers
Authorization: Bearer &lt;admin_access_token&gt;
```


#### Example Response:

```json
[
  {
    "lockerNumber": 101,
    "status": "Available"
  },
  {
    ...
  }
]
```

---

### 14. **Update Locker Details or Status**

**PUT** `/api/admin/lockers/{lockerNumber}`
Updates the details or status of a specific locker.

#### Example Request:

```json
PUT /api/admin/lockers/101
Content-Type: application/json

{
  	"status":"Under Maintenance"
}
```

---

