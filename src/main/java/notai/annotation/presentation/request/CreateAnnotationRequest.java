package notai.annotation.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAnnotationRequest {
    private int pageNumber;
    private int x;
    private int y;
    private int width;
    private int height;
    private String content;
}
