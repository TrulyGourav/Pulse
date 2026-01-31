
# 🔐 Authentication & JWT Login Flow (Industry-Grade Explanation)

This document explains the **complete login flow** used in this project - exactly how **big tech companies** design authentication.


## 1️⃣ Need for this Auth service?

We want:
- Secure authentication
- Stateless backend (scalable)
- No password sent again and again
- Ability to expire access safely

👉 Solution: **JWT-based Authentication**

---

## 2️⃣ Key Concepts (Before Flow)

### 🔑 Access Token
- Short-lived (5–15 minutes)
- Sent with **every API request**
- Used for authorization

### 🔁 Refresh Token
- Long-lived (days)
- Used **only** to get a new access token
- Not sent on normal APIs

---

## 3️⃣ Full Login Flow (Step-by-Step)

### 🟢 STEP 1: User Login

```
POST /auth/login
{
  "email": "user@example.com",
  "password": "password123"
}
```

Backend:
- Finds user by email
- Verifies password using BCrypt
- If valid → generate JWT

---

### 🟢 STEP 2: Backend Generates Access Token

JWT contains:
- `sub` → user email
- `role` → ROLE_USER
- `iat` → issued time
- `exp` → expiry time

Example:
```
eyJhbGciOiJIUzI1NiJ9...
```

---

### 🟢 STEP 3: Client Stores Token

Best practice:
- Web → Memory / Secure Cookie
- Mobile → Secure Storage

❌ Avoid LocalStorage in serious systems

---

### 🟢 STEP 4: Accessing Secured APIs

```
GET /analytics/events
Authorization: Bearer <access-token>
```

---

### 🟢 STEP 5: JWT Filter Executes

Backend:
- Extracts token
- Validates signature
- Checks expiry
- Extracts user + role
- Populates SecurityContext

➡️ Request is now authenticated

---

### 🟢 STEP 6: Access Token Expires

After expiry:
```
HTTP 401 Unauthorized
```

User is NOT logged out yet.

---

## 4️⃣ Refresh Token Flow (Why It Exists)

### ❓ Why Not One Long Token?

❌ Long token = security risk  
If stolen → attacker has long access

✅ Short access + refresh token:
- Limits damage
- Improves UX
- Industry standard

---

### 🔁 Refresh Flow

1. Access token expires
2. Client calls:

```
POST /auth/refresh
Authorization: Bearer <refresh-token>
```

3. Backend:
- Validates refresh token
- Issues new access token
- (Optionally rotates refresh token)

4. Client retries original request

---

## 5️⃣ What We Implemented in This Project

### ✅ Implemented
✔ JWT Access Token  
✔ Stateless authentication  
✔ JWT validation filter  
✔ Token regeneration logic