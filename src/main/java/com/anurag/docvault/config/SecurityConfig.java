@Configuration @EnableWebSecurity @EnableMethodSecurity
public class SecurityConfig {

    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())          // CSRF off — JWT handles security
                .cors(cors -> cors.configure(http))     // Use existing CorsConfig bean
                .authorizeHttpRequests(auth -> auth
                        // Static frontend files — public
                        .requestMatchers('/', '/index.html', '/css/**', '/js/**').permitAll()
                        // Auth API — public
                        .requestMatchers('/auth/**').permitAll()
                        // Protected APIs
                        .requestMatchers('/admin/**').hasRole('ADMIN')
                        .requestMatchers('/user/**').hasRole('USER')
                        .anyRequest().authenticated()
                )
                .sessionManagement(STATELESS)           // No server-side sessions
                .addFilterBefore(jwtAuthFilter, ...);   // Run JWT check first
    }
}
