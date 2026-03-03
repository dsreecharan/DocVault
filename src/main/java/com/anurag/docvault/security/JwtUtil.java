// GENERATE — called after login/signup
public String generateToken(String email, String role) {
    return Jwts.builder()
            .setClaims(Map.of('role', role))  // Embed role in token payload
            .setSubject(email)                // Token subject = user's email
            .setIssuedAt(new Date())
            .setExpiration(new Date(now + expiration))
            .signWith(getSigningKey(), HS256)  // HMAC-SHA256 digital signature
            .compact();  // Returns 'xxxxx.yyyyy.zzzzz' string
}

// VALIDATE — called on every protected request
public boolean isTokenValid(String token, UserDetails userDetails) {
    String email = extractEmail(token);  // Decode subject from payload
    return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
}
