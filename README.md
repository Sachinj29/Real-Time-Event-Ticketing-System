# 🎟️ EventTickets - Event Ticketing System

A **full-stack event ticketing platform** built with **Spring Boot (backend)** and **React (frontend)**, featuring **Google OAuth2 authentication**, **role-based access control**, and **comprehensive booking management**.  

---

## 🚀 Project Overview

**EventTickets** is a modern ticketing platform where:  
- Users can **discover events, book tickets, and manage bookings**.  
- Event organizers can **create and manage events**.  
- Administrators have **full platform control**.  

---

## ✨ Key Features

- 🔐 **Multi-Authentication**: Email/Password + Google OAuth2  
- 👥 **Role-Based Access Control**: Participant, Organizer, Admin  
- 🎪 **Event Management**: Create, edit, and manage events with venue integration  
- 🎫 **Booking System**: Secure booking with seat & capacity management  
- 💳 **Payment Ready**: Easily integrable with gateways (Stripe)  
- 📱 **Responsive Design**: Mobile-friendly React frontend  
- 🐳 **Docker Ready**: Containerized for deployment  
- ☁️ **Cloud Hosted**: Backend deployed on **Render.com**  

---

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 3.x (Java 21)  
- **Security**: Spring Security + JWT  
- **Database**: PostgreSQL (prod)  
- **OAuth2**: Google OAuth2 integration  
- **Docs**: Swagger
- **Build Tool**: Maven  

### Frontend
- **Framework**: React 18 + TypeScript  
- **UI Library**: Material-UI (MUI)  
- **State Management**: Zustand  
- **HTTP Client**: Axios  
- **Routing**: React Router DOM  
- **Build Tool**: Vite  

### DevOps & Deployment
- **Containerization**: Docker  
- **Backend Hosting**: Render.com  
- **Frontend Hosting**: Vercel 
- **Version Control**: Git & GitHub  

---

## 🔑 API Endpoints

### Authentication
- `POST /api/auth/register` → User registration  
- `POST /api/auth/login` → User login  
- `GET /oauth2/authorization/google` → Google OAuth login  
- `GET /oauth2/authorization/google?role=ORGANIZER` → Organizer login via Google  

### Events
- `GET /api/events` → List all events (public)  
- `GET /api/events/{id}` → Event details  
- `POST /api/events` → Create event (organizer only)  
- `PUT /api/events/{id}` → Update event (organizer only)  
- `DELETE /api/events/{id}` → Delete event (organizer only)  

### Bookings
- `GET /api/bookings` → User bookings (authenticated)  
- `POST /api/bookings` → Create booking (authenticated)  
- `DELETE /api/bookings/{id}` → Cancel booking (authenticated)  

### Admin
- `GET /api/admin/users` → Manage users (admin only)  
- `GET /api/admin/events` → Manage all events (admin only)  

---

## 🚀 Getting Started

### ✅ Prerequisites
- Java 21+  
- Node.js 18+  
- Docker (optional)  
- Google OAuth2 credentials  

---

### ⚙️ Backend Setup
```bash
# Clone the repository
git clone https://github.com/yourusername/event-ticketing-system.git
cd event-ticketing-system

# Configure Google OAuth2 in Google Cloud Console
# Add redirect URI: http://localhost:8080/login/oauth2/code/google

# Set environment variables
export GOOGLE_CLIENT_ID=your_google_client_id
export GOOGLE_CLIENT_SECRET=your_google_client_secret
export JWT_SECRET=your_secret_key

# Run backend
cd backend
./mvnw spring-boot:run
