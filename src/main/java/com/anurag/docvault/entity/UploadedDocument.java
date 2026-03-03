@Document(collection = 'documents')
public class UploadedDocument {
    @Id private String id;
    private String fileName;       // Original name e.g. 'report.pdf'
    private String filePath;       // Absolute path on disk e.g. '/app/uploads/uuid_report.pdf'
    private String uploadedBy;     // Email of uploader — links to User
    private String summary;        // AI-generated text summary
    private LocalDateTime uploadedAt;  // Upload timestamp
}
