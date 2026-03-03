@Service
public class CustomUserDetailsService implements UserDetailsService {
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException('Not found: ' + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                // CRITICAL: Spring Security requires 'ROLE_' prefix
                List.of(new SimpleGrantedAuthority('ROLE_' + user.getRole()))
                // So 'ADMIN' becomes 'ROLE_ADMIN', matching hasRole('ADMIN')
        );
    }
}
