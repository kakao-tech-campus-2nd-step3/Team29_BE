package notai.pdf;

import lombok.RequiredArgsConstructor;
import notai.common.exception.type.FileProcessException;
import notai.common.exception.type.NotFoundException;
import notai.pdf.result.PdfSaveResult;
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
            file.transferTo(filePath.toFile());

            return PdfSaveResult.of(fileName, filePath.toFile());
        } catch (IOException exception) {
            throw new FileProcessException("자료를 저장하는 과정에서 에러가 발생했습니다.");
        }
    }

    public File getPdf(String fileName) {
        Path filePath = Paths.get(STORAGE_DIR, fileName);

        if (!Files.exists(filePath)) {
            throw new NotFoundException("존재하지 않는 파일입니다.");
        }
        return filePath.toFile();
    }
}
