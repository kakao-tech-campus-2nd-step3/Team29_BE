package notai.client.ai;

import lombok.RequiredArgsConstructor;
import notai.client.ai.request.LlmTaskRequest;
import notai.client.ai.response.TaskResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class AiClientImpl implements AiClient {

    private final AiClient aiClient;

    @Override
    public TaskResponse submitLlmTask(LlmTaskRequest request) {
        return aiClient.submitLlmTask(request);
    }

    @Override
    public TaskResponse submitSttTask(MultipartFile audioFile) {
        return aiClient.submitSttTask(audioFile);
    }
}
