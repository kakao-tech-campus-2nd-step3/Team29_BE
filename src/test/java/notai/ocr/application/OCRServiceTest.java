package notai.ocr.application;

import notai.document.domain.Document;
import notai.ocr.domain.OCR;
import notai.ocr.domain.OCRRepository;
import notai.pdf.result.PdfSaveResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ExtendWith(MockitoExtension.class)
class OCRServiceTest {

    @InjectMocks
    OCRService ocrService;
    @Mock
    OCRRepository ocrRepository;

    @Test
    void savePdf_success_existsTestPdf() throws IOException {
        //given
        Document document = mock(Document.class);
        OCR ocr = mock(OCR.class);
        ClassPathResource existsPdf = new ClassPathResource("pdf/test.pdf");
        PdfSaveResult saveResult = PdfSaveResult.of("test.pdf", existsPdf.getFile());
        when(ocrRepository.save(any(OCR.class))).thenReturn(ocr);
        //when
        ocrService.saveOCR(document, saveResult.pdf());
        //then
        verify(ocrRepository, times(43)).save(any(OCR.class));

        deleteFile(saveResult.pdf().toPath());
    }

    void deleteFile(Path filePath) throws IOException {
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }
}
