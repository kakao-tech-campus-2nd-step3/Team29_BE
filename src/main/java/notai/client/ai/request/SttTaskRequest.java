package notai.client.ai.request;

import org.springframework.web.multipart.MultipartFile;

public record SttTaskRequest(
        MultipartFile audioFile
) {
}
