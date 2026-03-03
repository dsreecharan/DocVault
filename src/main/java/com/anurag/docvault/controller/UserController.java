@RestController
@RequestMapping('/user')
public class UserController {

    @PostMapping('/upload')      // POST /user/upload (multipart/form-data)
    public ResponseEntity<DocumentDTO> upload(
            @RequestParam('file') MultipartFile file,
            @AuthenticationPrincipal UserDetails userDetails) {
        // userDetails.getUsername() returns the email extracted from the JWT
        return ResponseEntity.ok(
                documentService.uploadDocument(file, userDetails.getUsername()));
    }

    @GetMapping('/documents')    // GET /user/documents
    public ResponseEntity<List<DocumentDTO>> getMyDocs(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                documentService.getDocumentsByUser(userDetails.getUsername()));
    }
}
