@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = 'users')
public class User {
    @Id                          // MongoDB _id field
    private String id;
    private String name;
    @Indexed(unique = true)      // MongoDB unique index on email
    private String email;
    private String password;     // BCrypt hash — NEVER plain text
    private String role;         // 'USER' or 'ADMIN'
}
