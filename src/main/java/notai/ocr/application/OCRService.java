package notai.ocr.application;

import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import notai.common.exception.type.FileProcessException;
import notai.document.domain.Document;
import notai.ocr.domain.OCR;
import notai.ocr.domain.OCRRepository;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;

@Service
@RequiredArgsConstructor
public class OCRService {

    private final OCRRepository ocrRepository;

    @Async
    public void saveOCR(
            Document document, File pdfFile
    ) {
        try {
            System.setProperty("jna.library.path", "/usr/local/opt/tesseract/lib/");
            //window, mac -> brew install tesseract, tesseract-lang
            Tesseract tesseract = new Tesseract();

            tesseract.setDatapath("/usr/local/share/tessdata");
            tesseract.setLanguage("kor+eng");

            PDDocument pdDocument = Loader.loadPDF(pdfFile);
            PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
            for (int i = 0; i < pdDocument.getNumberOfPages(); i++) {
                BufferedImage image = pdfRenderer.renderImage(i);
                String ocrResult = tesseract.doOCR(image);
                OCR ocr = new OCR(document, i + 1, ocrResult);
                ocrRepository.save(ocr);
            }

            pdDocument.close();
        } catch (Exception e) {
            throw new FileProcessException("PDF 파일을 통해 OCR 작업을 수행하는데 실패했습니다.");
        }
    }
}
