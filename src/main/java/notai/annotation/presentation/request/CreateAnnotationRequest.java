package notai.annotation.presentation.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateAnnotationRequest(

        @Positive(message = "페이지 번호는 양수여야 합니다.")
        int pageNumber,

//        @Max(value = ?, message = "x 좌표는 최대 ? 이하여야 합니다.")
        @PositiveOrZero(message = "x 좌표는 0 이상이어야 합니다.")
        int x,

//        @Max(value = ?, message = "x 좌표는 최대 ? 이하여야 합니다.")
        @PositiveOrZero(message = "y 좌표는 0 이상이어야 합니다.")
        int y,

//        @Max(value = ?, message = "width는 최대 ? 이하여야 합니다.")
        @Positive(message = "width는 양수여야 합니다.")
        int width,

//        @Max(value = ?, message = "height는 최대 ? 이하여야 합니다.")
        @Positive(message = "height는 양수여야 합니다.")
        int height,

        String content
) {}
