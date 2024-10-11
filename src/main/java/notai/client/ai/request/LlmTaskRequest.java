package notai.client.ai.request;

public record LlmTaskRequest(
        String ocrText,
        String stt,
        String keyboardNote
) {
    public static LlmTaskRequest of(String ocrText, String stt, String keyboardNote) {
        return new LlmTaskRequest(ocrText, stt, keyboardNote);
    }
}
