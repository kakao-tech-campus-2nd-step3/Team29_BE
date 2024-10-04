package notai.annotation.presentation.response;

import notai.annotation.domain.Annotation;

public record AnnotationResponse(
        Long id,
        Long documentId,
        int pageNumber,
        int x,
        int y,
        int width,
        int height,
        String content,
        String createdAt,
        String updatedAt
) {

    public static AnnotationResponse from(Annotation annotation) {
        return new AnnotationResponse(
                annotation.getId(),
                annotation.getDocument().getId(),
                annotation.getPageNumber(),
                annotation.getX(),
                annotation.getY(),
                annotation.getWidth(),
                annotation.getHeight(),
                annotation.getContent(),
                annotation.getCreatedAt().toString(),
                annotation.getUpdatedAt().toString()
        );
    }
}
