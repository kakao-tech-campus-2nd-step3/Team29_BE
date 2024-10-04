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

        AnnotationResponse response = annotationService.createAnnotation(
                documentId,
                request.pageNumber(),
                request.x(),
                request.y(),
                request.width(),
                request.height(),
                request.content()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<AnnotationResponse>> getAnnotations(
            @PathVariable Long documentId,
            @RequestParam List<Integer> pageNumbers) {

        List<AnnotationResponse> response = annotationQueryService.getAnnotationsByDocumentAndPageNumbers(documentId, pageNumbers);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{annotationId}")
    public ResponseEntity<AnnotationResponse> updateAnnotation(
            @PathVariable Long documentId,
            @PathVariable Long annotationId,
            @RequestBody @Valid CreateAnnotationRequest request) {

        AnnotationResponse response = annotationService.updateAnnotation(
                documentId,
                annotationId,
                request.x(),
                request.y(),
                request.width(),
                request.height(),
                request.content()
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
