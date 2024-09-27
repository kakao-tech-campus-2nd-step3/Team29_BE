package notai.annotation.presentation.response;

import lombok.Getter;
import notai.annotation.domain.Annotation;

@Getter
public class AnnotationResponse {
    private Long id;
    private Long documentId;
    private int pageNumber;
    private int x;
    private int y;
    private int width;
    private int height;
    private String content;
    private String createdAt;
    private String updatedAt;

    public AnnotationResponse(Annotation annotation) {
        this.id = annotation.getId();
        this.documentId = annotation.getDocument().getId();
        this.pageNumber = annotation.getPageNumber();
        this.x = annotation.getX();
        this.y = annotation.getY();
        this.width = annotation.getWidth();
        this.height = annotation.getHeight();
        this.content = annotation.getContent();
        this.createdAt = annotation.getCreatedAt().toString();
        this.updatedAt = annotation.getUpdatedAt().toString();
    }
}
