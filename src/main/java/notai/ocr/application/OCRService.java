package notai.ocr.application;

import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import static notai.common.exception.ErrorMessages.OCR_TASK_ERROR;
import notai.common.exception.type.FileProcessException;
import notai.document.domain.Document;
import notai.ocr.domain.OCR;
import notai.ocr.domain.OCRRepository;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.io.File;

@Service
@RequiredArgsConstructor
@Transactional
public class OCRService {

    private final OCRRepository ocrRepository;

    @Value("${tesseract.library.path}")
    private String libraryPath;

    @Value("${tesseract.data.path}")
    private String dataPath;

    @Value("${tesseract.language}")
    private String language;

    @Async
    public void saveOCR(
            Document document, File pdfFile
    ) {
        try {
            System.setProperty("jna.library.path", libraryPath);

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(dataPath);
            tesseract.setLanguage(language);

            PDDocument pdDocument = Loader.loadPDF(pdfFile);
            PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
            for (int i = 0; i < pdDocument.getNumberOfPages(); i++) {
                BufferedImage image = pdfRenderer.renderImage(i);
                String ocrResult = tesseract.doOCR(image);
                OCR ocr = new OCR(document, i, ocrResult);
                ocrRepository.save(ocr);
            }

            pdDocument.close();
        } catch (Exception e) {
            throw new FileProcessException(OCR_TASK_ERROR);
        }
    }

    public void deleteAllByDocument(Document document) {
        ocrRepository.deleteAllByDocument(document);
    }
}
