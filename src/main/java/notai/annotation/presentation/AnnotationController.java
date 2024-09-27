package notai.annotation.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notai.annotation.application.AnnotationQueryService;
import notai.annotation.application.AnnotationService;
import notai.annotation.presentation.request.CreateAnnotationRequest;
import notai.annotation.presentation.response.AnnotationResponse;
import notai.common.exception.type.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents/{documentId}/annotations")
@RequiredArgsConstructor
public class AnnotationController {

    private final AnnotationService annotationService;
    private final AnnotationQueryService annotationQueryService;

    @PostMapping
    public ResponseEntity<AnnotationResponse> createAnnotation(
            @PathVariable Long documentId,
            @RequestBody @Valid CreateAnnotationRequest request) {

        if (request.getPageNumber() <= 0) {
            throw new BadRequestException("유효하지 않은 페이지 번호: " + request.getPageNumber());
        }

        AnnotationResponse response = annotationService.createAnnotation(
                documentId,
                request.getPageNumber(),
                request.getX(),
                request.getY(),
                request.getWidth(),
                request.getHeight(),
                request.getContent()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<AnnotationResponse>> getAnnotations(@PathVariable Long documentId) {

        List<AnnotationResponse> response = annotationQueryService.getAnnotationsByDocument(documentId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{annotationId}")
    public ResponseEntity<AnnotationResponse> updateAnnotation(
            @PathVariable Long documentId,
            @PathVariable Long annotationId,
            @RequestBody @Valid CreateAnnotationRequest request) {

        if (request.getX() < 0 || request.getY() < 0) {
            throw new BadRequestException("유효하지 않은 좌표 값: (" + request.getX() + ", " + request.getY() + ")");
        }

        AnnotationResponse response = annotationService.updateAnnotation(
                documentId,
                annotationId,
                request.getX(),
                request.getY(),
                request.getWidth(),
                request.getHeight(),
                request.getContent()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{annotationId}")
    public ResponseEntity<Void> deleteAnnotation(
            @PathVariable Long documentId,
            @PathVariable Long annotationId) {

        annotationService.deleteAnnotation(documentId, annotationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
