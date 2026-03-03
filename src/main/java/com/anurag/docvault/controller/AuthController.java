@RestController
@RequestMapping('/auth')       // All methods in this class live under /auth
public class AuthController {

    @PostMapping('/signup')      // Full path: POST /auth/signup
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest req) {
        return ResponseEntity.ok(authService.signup(req));
    }

    @PostMapping('/login')       // Full path: POST /auth/login
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
