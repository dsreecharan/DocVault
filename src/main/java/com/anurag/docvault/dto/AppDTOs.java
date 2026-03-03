UserDTO             { id, name, email, role }         // No password!
UpdateUserRequest   { name, email, role }             // Admin PUT /admin/user/{id}
DocumentDTO         { id, fileName, filePath,
        uploadedBy, summary, uploadedAt }
UpdateDocumentRequest { fileName, summary }           // Admin PUT /admin/document/{id}
MessageResponse     { message }                      // Simple text responses