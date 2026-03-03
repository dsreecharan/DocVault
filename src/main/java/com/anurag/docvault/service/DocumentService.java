public DocumentDTO uploadDocument(MultipartFile file, String userEmail) {
    // Step 1: Save file to disk → returns absolute path
    String filePath = fileStorageService.storeFile(file);

    // Step 2: Extract text content from the file
    String text = textExtractionService.extractText(file);

    // Step 3: Send text to AI → get summary paragraph
    String summary = aiSummaryService.summarize(text);

    // Step 4: Save metadata + summary to MongoDB
    UploadedDocument doc = UploadedDocument.builder()
            .fileName(file.getOriginalFilename())
            .filePath(filePath)
            .uploadedBy(userEmail)
            .summary(summary)
            .uploadedAt(LocalDateTime.now())
            .build();
    return toDTO(documentRepository.save(doc));
}
