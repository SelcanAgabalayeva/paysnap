# 💳 PaySnap — QR-Based Payment Link Generator API

PaySnap is a secure and modular payment system built with Spring Boot that enables users to create Stripe payment sessions, generate QR codes, and track payments in real time. It provides a full backend solution for modern contactless payment workflows with authentication, order tracking, and automated receipt generation.

---

## 🚀 Features

### 💰 Payment System
- Create Stripe Checkout Sessions
- Generate secure payment links
- Track payment status in real time

### 📱 QR Code Generation
- Generate QR codes using ZXing
- QR codes encode Stripe payment URLs
- Mobile-friendly contactless payments

### 🔐 Authentication & Security
- JWT-based authentication
- Redis-backed token blacklist system
- Secure logout with token invalidation

### 🔔 Webhooks
- Stripe webhook listener
- Real-time payment status updates
- Automatic order status synchronization

### 📄 PDF Receipts
- Automatic receipt generation after successful payment
- Downloadable PDF invoices
- Email receipt support (optional)

### 📦 Order Management
- Create and manage orders
- View payment history
- Track status: pending, paid, canceled

---

## 🧠 Learning Objectives

- Stripe Java SDK integration
- QR code generation using ZXing
- JWT authentication with Redis blacklist
- Webhook-based event handling
- PDF generation in Spring Boot
- Secure API design and role-based access control

---

## 🛠 Tech Stack

- Java 17+
- Spring Boot
- Spring Security + JWT
- Stripe Java SDK
- PostgreSQL
- Redis
- ZXing (QR Code Generator)
- Docker & Docker Compose
- Swagger / OpenAPI

---

## 🐳 Docker Setup

```bash
docker-compose up -d
