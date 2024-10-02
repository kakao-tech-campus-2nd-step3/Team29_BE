package notai.common.utils;

import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileManager {

    public void save(byte[] binaryFile, Path path) throws IOException {
        Files.createDirectories(path.getParent());

        try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
            fos.write(binaryFile);
        }
    }
}
