private final Tika tika = new Tika();

public String extractText(MultipartFile file) {
    String text = tika.parseToString(file.getInputStream());
    // Limit to 3000 chars to keep AI API prompts manageable
    return text.length() > 3000 ? text.substring(0, 3000) : text;
}
