package notai.sttTask.application.command;

public record SttTaskPageStatusCommand(
        Long documentId,
        Integer pageNumber
) {
    public static SttTaskPageStatusCommand of(Long documentId, Integer pageNumber) {
        return new SttTaskPageStatusCommand(documentId, pageNumber);
    }
}
