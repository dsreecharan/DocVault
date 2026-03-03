package com.anurag.docvault.service;

import com.anurag.docvault.exception.AppException;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class TextExtractionService {

    private final Tika tika = new Tika();

    public String extractText(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            String text = tika.parseToString(is);
            return text.length() > 3000 ? text.substring(0, 3000) : text;
        } catch (Exception e) {
            throw new AppException("Failed to extract text: " + e.getMessage());
        }
    }
}
