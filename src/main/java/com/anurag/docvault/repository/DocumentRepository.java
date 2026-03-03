package com.anurag.docvault.repository;

import com.anurag.docvault.entity.UploadedDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends MongoRepository<UploadedDocument, String> {
    List<UploadedDocument> findByUploadedBy(String email);
}
