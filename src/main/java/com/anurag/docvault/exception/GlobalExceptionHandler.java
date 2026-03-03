@RestControllerAdvice  // Applied to all @RestController classes
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)        // Our custom errors
  → 400 Bad Request   { 'error': ex.getMessage() }

    @ExceptionHandler(BadCredentialsException.class)  // Wrong password
  → 401 Unauthorized  { 'error': 'Invalid email or password' }

    @ExceptionHandler(AccessDeniedException.class)    // Wrong role
  → 403 Forbidden     { 'error': 'Access denied' }

    @ExceptionHandler(Exception.class)           // Unexpected errors
  → 500 Server Error  { 'error': ex.getMessage() }
}
