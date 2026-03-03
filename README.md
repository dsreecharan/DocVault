📚 DocVault

🔐 Secure AI-Powered Document Management & Summarization System
🚀 Overview

DocVault is a secure, full-stack document management system that allows users to upload documents, extract text, and generate AI-powered summaries using a locally hosted Llama3 model via Ollama.

The system implements JWT-based authentication, role-based access control, and offline AI processing, ensuring both security and privacy.

🌍 Real-World Problem

Organizations and individuals handle large volumes of documents such as research papers, reports, legal files, and academic materials.

Problems with existing systems:

❌ Lack of secure role-based access control

❌ Manual reading of lengthy documents

❌ No intelligent summarization

❌ Dependency on cloud AI APIs (privacy concerns)

DocVault solves this by providing:

Secure document storage

Role-based user access

Automated AI summarization

Fully offline AI processing using Ollama

🏗 Architecture
User
  ↓
Frontend (HTML/CSS/JS)
  ↓
Spring Boot Backend
  ↓
JWT Authentication
  ↓
File Storage (Local Disk)
  ↓
Apache Tika (Text Extraction)
  ↓
Ollama (Llama3 Local AI)
  ↓
MongoDB (Metadata Storage)
  ↓
Summary Displayed to User
🛠 Tech Stack
🔹 Backend

Java 17+

Spring Boot 3

Spring Security

JWT Authentication

MongoDB

Apache Tika

🔹 AI Integration

Ollama

Llama3 (Local LLM)

REST API Integration

🔹 Frontend

HTML5

CSS3 (Custom Design System)

Vanilla JavaScript (SPA-style behavior)

🔐 Security Features

JWT-based stateless authentication

Role-Based Access Control (USER / ADMIN)

BCrypt password hashing

Protected API endpoints

Global exception handling

No external AI data exposure

👥 User Roles
🧑 USER

Upload documents

View personal documents

View AI summaries

👨‍💼 ADMIN

View all users

View all documents

Edit user details

Edit document metadata

📂 Project Structure
DocVault/
│
├── src/main/java/com/example/docvault
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   ├── security/
│   ├── config/
│   └── exception/
│
├── src/main/resources/
│   ├── application.properties
│   └── static/
│       ├── index.html
│       ├── css/
│       └── js/
│
├── uploads/
├── pom.xml
└── README.md
⚙️ Installation & Setup
1️⃣ Clone the Repository
git clone https://github.com/dsreecharan/DocVault.git
cd DocVault
2️⃣ Install Dependencies

Ensure you have:

Java 17+

Maven

MongoDB (running locally)

Ollama installed

3️⃣ Pull Llama3 Model
ollama pull llama3
4️⃣ Start Ollama
ollama run llama3

Or ensure the Ollama server is running:

http://localhost:11434
5️⃣ Configure application.properties
spring.data.mongodb.uri=mongodb://localhost:27017/docvault

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000

file.upload-dir=uploads

ai.api.url=http://localhost:11434/api/generate
ai.model=llama3
6️⃣ Run the Application
mvn spring-boot:run

Visit:

http://localhost:8080
🧠 How AI Summarization Works

User uploads document

Apache Tika extracts text

Text is trimmed (token control)

Sent to Ollama Llama3 API

Summary returned

Metadata stored in MongoDB

All processing happens locally.

📊 Features

Secure Login & Signup

Document Upload (PDF, DOCX, TXT, etc.)

AI Summary Generation

Searchable Document View

Admin Dashboard

Clean UI Design

Toast Notifications

Modal Editing
