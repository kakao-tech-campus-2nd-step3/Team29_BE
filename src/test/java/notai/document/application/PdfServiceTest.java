package notai.document.application;

import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ExtendWith(MockitoExtension.class)
class PdfServiceTest {

    @InjectMocks
    PdfService pdfService;

    static final String STORAGE_DIR = "src/main/resources/pdf/";

    @Test
    void savePdf_success_existsTestPdf() throws IOException {
        //given
        ClassPathResource existsPdf = new ClassPathResource("pdf/test.pdf");
        MockMultipartFile mockFile = new MockMultipartFile("file",
                existsPdf.getFilename(),
                "application/pdf",
                Files.readAllBytes(existsPdf.getFile().toPath())
        );
        //when
        String savedFileName = pdfService.savePdf(mockFile);
        //then
        Path savedFilePath = Paths.get(STORAGE_DIR, savedFileName);
        Assertions.assertThat(Files.exists(savedFilePath)).isTrue();

        System.setProperty("jna.library.path", "/usr/local/opt/tesseract/lib/");
        //window, mac -> brew install tesseract, tesseract-lang
        Tesseract tesseract = new Tesseract();

        tesseract.setDatapath("/usr/local/share/tessdata");
        tesseract.setLanguage("kor+eng");

        try {
            PDDocument pdDocument = Loader.loadPDF(savedFilePath.toFile());
            PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);

            var image = pdfRenderer.renderImage(9);
            var start = System.currentTimeMillis();
            var ocrResult = tesseract.doOCR(image);
            System.out.println("result : " + ocrResult);
            var end = System.currentTimeMillis();
            System.out.println(end - start);
            pdDocument.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        deleteFile(savedFilePath);
    }

    void deleteFile(Path filePath) throws IOException {
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }
}
