package notai.pdf;

import lombok.RequiredArgsConstructor;
import notai.common.exception.ErrorMessages;
import static notai.common.exception.ErrorMessages.FILE_NOT_FOUND;
import static notai.common.exception.ErrorMessages.FILE_SAVE_ERROR;
import notai.common.exception.type.FileProcessException;
import notai.common.exception.type.NotFoundException;
import notai.pdf.result.PdfSaveResult;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PdfService {

    private static final String STORAGE_DIR = "src/main/resources/pdf/";

    public PdfSaveResult savePdf(MultipartFile file) {
        try {
            Path directoryPath = Paths.get(STORAGE_DIR);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            String fileName = UUID.randomUUID() + ".pdf";
            Path filePath = directoryPath.resolve(fileName);
            File pdfFile = filePath.toFile();
            file.transferTo(pdfFile);
            Integer totalPages = getTotalPages(pdfFile);
            return PdfSaveResult.of(fileName, pdfFile, totalPages);
        } catch (IOException exception) {
            throw new FileProcessException(FILE_SAVE_ERROR);
        }
    }

    public File getPdf(String fileName) {
        Path filePath = Paths.get(STORAGE_DIR, fileName);

        if (!Files.exists(filePath)) {
            throw new NotFoundException(FILE_NOT_FOUND);
        }
        return filePath.toFile();
    }

    private Integer getTotalPages(File file) {
        try {
            PDDocument document = Loader.loadPDF(file);
            Integer totalPages = document.getNumberOfPages();
            document.close();
            return totalPages;
        } catch (IOException e) {
            throw new FileProcessException(ErrorMessages.INVALID_FILE_TYPE);
        }
    }
}
