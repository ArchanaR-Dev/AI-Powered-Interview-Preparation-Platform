# AI-Powered Interview Preparation Platform
 
A full-stack web application that lets users practice technical interviews with AI-generated questions, real-time answer evaluation, and a final performance report — tailored to a chosen role and difficulty level.
 
## Features
 
- **User authentication** — registration and login secured with BCrypt password hashing and stateless JWT-based authentication
- **Interview setup** — users select a role (e.g. Backend Developer, Data Analyst) and a difficulty level (Easy / Medium / Hard)
- **AI-generated questions** — questions are dynamically generated per session using Google's Gemini API, tailored to the chosen role and difficulty
- **Answer evaluation** — each submitted answer is evaluated by AI and returns technical feedback, communication feedback, missing points, and a score
- **Final interview report** — a holistic, AI-generated summary across the full session: overall score, technical performance, communication performance, missing topics, recommended topics, and overall feedback
- **Authorization** — all interview and question data is scoped to the authenticated user via JWT; users cannot access or modify another user's data
## Tech Stack
 
**Backend**
- Java, Spring Boot
- Spring Security (JWT-based, stateless)
- Spring Data JPA / Hibernate
- MySQL
- Google Gemini API (via REST)
**Frontend**
- Angular (standalone components)
- Template-driven forms
- RxJS
## Architecture
 
The backend follows a layered architecture:
 
```
Controller  →  Service  →  Repository  →  Database
     ↑             ↓
   DTOs      AiClient (Gemini integration)
```
 
- **Entities**: `User`, `Interview`, `InterviewQuestion`, `InterviewReport`
- **DTOs** separate the API's public shape from internal entity structure (e.g. `UserResponse` excludes the password hash)
- **`AiClient`** is an interface abstraction around the AI provider, implemented by `GeminiAiClient` — this keeps the AI provider swappable without touching service logic
- **JWT flow**: on login, a signed token (user email + user ID as claims) is issued. A custom `JwtAuthFilter` validates the token on every protected request and populates Spring Security's authentication context, so controllers can resolve the current user without trusting client-supplied IDs
### Request flow for a typical interview session
 
1. User registers / logs in → receives a JWT
2. User submits role + difficulty → backend creates an `Interview` record
3. Frontend requests questions → backend calls Gemini to generate questions for that role/difficulty, saves them, returns them
4. User answers each question → backend calls Gemini to evaluate the answer, saves feedback and score
5. Once all questions are answered, user requests the final report → backend aggregates all Q&A, calls Gemini for a holistic summary, saves and returns the report
## Setup
 
### Prerequisites
- Java 17+
- Node.js and npm
- MySQL
- A Google Gemini API key ([aistudio.google.com](https://aistudio.google.com))
### Backend
 
1. Create a MySQL database and update `src/main/resources/application.properties` with your database URL, username, and password.
2. Set your Gemini API key as an environment variable:
```
   GEMINI_API_KEY=your-api-key-here
```
3. From the project root:
```bash
   mvn spring-boot:run
```
   The backend runs on `http://localhost:8080`.
 
### Frontend
 
1. Navigate to the frontend directory:
```bash
   cd frontend
   npm install
```
2. Start the dev server:
```bash
   ng serve
```
   The app runs on `http://localhost:4200`.
 
## API Overview
 
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/sign-up` | Register a new user |
| POST | `/api/auth/log-in` | Log in, returns a JWT |
| POST | `/api/interview` | Create a new interview (role, difficulty) |
| GET | `/api/interview/user/{userId}` | Get all interviews for a user |
| POST | `/api/interviews/{interviewId}/questions/generate` | Generate AI questions for an interview |
| GET | `/api/interviews/{interviewId}/questions` | Get all questions for an interview |
| POST | `/api/questions/{questionId}/answer` | Submit an answer, receive AI evaluation |
| POST | `/api/interviews/{interviewId}/report/generate` | Generate the final AI report |
| GET | `/api/interviews/{interviewId}/report` | Retrieve an existing report |
 
All endpoints except `/api/auth/**` require an `Authorization: Bearer <token>` header.
 
## Known Limitations / Future Improvements
 
- No automated test coverage yet
- JWT signing key is regenerated on every backend restart (invalidates existing sessions) — a fixed secret from configuration would be needed for a production deployment
- Question count is currently a fixed constant; making it user-configurable would be a natural next step
- Not yet deployed to a live environment
- No password reset / email verification flow
## License
 
This project was built as a personal learning and portfolio project.
