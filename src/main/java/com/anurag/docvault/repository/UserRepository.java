public interface UserRepository extends MongoRepository<User, String> {
    // Method name is parsed: find + By + Email → WHERE email = ?
    Optional<User> findByEmail(String email);

    // existsBy generates: SELECT COUNT(*) > 0 WHERE email = ?
    boolean existsByEmail(String email);
}
