package com.example.docvault.service;

import com.example.docvault.dto.AppDTOs;
import com.example.docvault.entity.UploadedDocument;
import com.example.docvault.exception.AppException;
import com.example.docvault.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;
    private final TextExtractionService textExtractionService;
    private final AiSummaryService aiSummaryService;

    public AppDTOs.DocumentDTO uploadDocument(MultipartFile file, String userEmail) {
        String filePath     = fileStorageService.storeFile(file);
        String extractedText = textExtractionService.extractText(file);
        String summary      = aiSummaryService.summarize(extractedText);
        UploadedDocument doc = UploadedDocument.builder()
                .fileName(file.getOriginalFilename())
                .filePath(filePath)
                .uploadedBy(userEmail)
                .summary(summary)
                .uploadedAt(LocalDateTime.now())
                .build();
        return toDTO(documentRepository.save(doc));
    }

    public List<AppDTOs.DocumentDTO> getDocumentsByUser(String email) {
        return documentRepository.findByUploadedBy(email)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<AppDTOs.DocumentDTO> getAllDocuments() {
        return documentRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AppDTOs.DocumentDTO updateDocument(String id, AppDTOs.UpdateDocumentRequest req) {
        UploadedDocument doc = documentRepository.findById(id)
                .orElseThrow(() -> new AppException("Document not found"));
        if (req.getFileName() != null) doc.setFileName(req.getFileName());
        if (req.getSummary()  != null) doc.setSummary(req.getSummary());
        return toDTO(documentRepository.save(doc));
    }

    private AppDTOs.DocumentDTO toDTO(UploadedDocument d) {
        return new AppDTOs.DocumentDTO(d.getId(), d.getFileName(),
                d.getFilePath(), d.getUploadedBy(), d.getSummary(), d.getUploadedAt());
    }
}
