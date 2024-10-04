package notai.document.presentation;

import lombok.RequiredArgsConstructor;
import notai.document.application.PdfService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

@Controller
@RequestMapping("/pdf")
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;

    @GetMapping("/{fileName}")
    public ResponseEntity<FileSystemResource> getPdf(@PathVariable String fileName) {
        File pdf = pdfService.getPdf(fileName);
        FileSystemResource pdfResource = new FileSystemResource(pdf);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName).contentType(
                MediaType.APPLICATION_PDF).body(pdfResource);
    }
}
