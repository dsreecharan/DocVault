// POST /auth/signup body
SignupRequest  { name, email, password }

// POST /auth/login body
LoginRequest   { email, password }

// Response from both login and signup
AuthResponse   { token, role, email }
// The frontend saves token → Authorization header
//              role   → decides which pages to show
//              email  → displayed in sidebar
