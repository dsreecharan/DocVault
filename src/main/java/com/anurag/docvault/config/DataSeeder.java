@Component
public class DataSeeder implements CommandLineRunner {
    public void run(String... args) {
        if (userRepository.existsByEmail('admin@docvault.com')) return; // Skip if exists
        userRepository.save(User.builder()
                .email('admin@docvault.com')
                .password(passwordEncoder.encode('admin123'))  // BCrypt hashed
                .role('ADMIN').build());
    }
}
