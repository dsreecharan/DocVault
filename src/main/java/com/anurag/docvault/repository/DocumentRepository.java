public interface DocumentRepository extends MongoRepository<UploadedDocument, String> {
    // findBy + UploadedBy → db.documents.find({ uploadedBy: email })
    List<UploadedDocument> findByUploadedBy(String email);
}
