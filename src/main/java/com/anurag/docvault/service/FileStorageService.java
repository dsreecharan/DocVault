public String storeFile(MultipartFile file) {
    Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
    Files.createDirectories(uploadPath);  // Create folder if it doesn't exist

    // UUID prefix prevents name collisions
    String uniqueName = UUID.randomUUID() + '_' + file.getOriginalFilename();
    Path filePath = uploadPath.resolve(uniqueName);
    Files.copy(file.getInputStream(), filePath);
    return filePath.toString();
}
