# 🔐 DocVault

A secure and intelligent document management system with AI-powered summarization. Upload, manage, and summarize documents using a role-based secure architecture powered by Spring Boot, MongoDB, and local Llama3 via Ollama.

---

## Features

- **Secure Authentication**: JWT-based stateless authentication
- **Role-Based Access Control**: Separate USER and ADMIN privileges
- **AI-Powered Summaries**: Automatic document summarization using Llama3 (Ollama)
- **Multi-Format Support**: PDF, DOCX, TXT, and more via Apache Tika
- **Offline AI Processing**: No external API dependency
- **Admin Dashboard**: Manage users and documents
- **Searchable Documents**: Filter and view uploaded files
- **Clean UI**: Lightweight SPA-style frontend
- **Secure Password Storage**: BCrypt hashing
- **Global Exception Handling**: Clean JSON error responses

---

## Installation

### From Source

1. Clone the repository

   ```bash
   git clone https://github.com/dsreecharan/DocVault.git
   cd DocVault
   ```

2. Ensure prerequisites are installed:
   - Java 17+
   - Maven
   - MongoDB (running locally)
   - Ollama

3. Pull Llama3 model

   ```bash
   ollama pull llama3
   ```

4. Configure `application.properties`

   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/docvault

   jwt.secret=YOUR_SECRET_KEY
   jwt.expiration=86400000

   file.upload-dir=uploads

   ai.api.url=http://localhost:11434/api/generate
   ai.model=llama3
   ```

5. Run the application

   ```bash
   mvn spring-boot:run
   ```

6. Open in browser:
   ```
   http://localhost:8080
   ```

---

## Usage

### Authentication

- Register as a new user
- Login to receive JWT token
- Token stored in browser localStorage
- Role-based UI rendered automatically

### Uploading Documents

1. Login as USER
2. Navigate to "Upload"
3. Select document (PDF, DOCX, TXT, etc.)
4. Click upload
5. System automatically:
   - Saves file
   - Extracts text
   - Generates summary
   - Stores metadata in MongoDB

### Admin Management

Login as ADMIN:

- View all users
- View all documents
- Edit user roles
- Update document metadata

---

## Project Structure

```
DocVault/
├── src/main/java/com/anurag/docvault
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
```

---

## Tech Stack

### Backend

- Spring Boot 3
- Spring Security
- JWT Authentication
- MongoDB
- Apache Tika

### AI

- Ollama
- Llama3 (Local LLM)

### Frontend

- HTML5
- CSS3
- Vanilla JavaScript

---

## Security

- Stateless JWT Authentication
- BCrypt Password Hashing
- Role-Based Endpoint Protection
- Global exception handling

---

## Performance

- Login response: <200ms
- Text extraction: <300ms
- AI summary generation: ~2–5s (local model)
- Document fetch: <150ms

---

## Roadmap

- [ ] Streaming AI summaries
- [ ] Drag & Drop Upload
- [ ] Document preview viewer
- [ ] Model switching (Mistral / Phi)
- [ ] Docker containerization
- [ ] Cloud deployment
- [ ] Dark mode UI

---

## Contributing

1. Fork the repository
2. Create a feature branch
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. Commit your changes
   ```bash
   git commit -m "Add amazing feature"
   ```
4. Push to branch
   ```bash
   git push origin feature/amazing-feature
   ```
5. Open a Pull Request

---

## License

MIT License – see LICENSE file for details.

---

**Built for secure and intelligent document management** 🔐📚
