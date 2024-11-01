package notai.client.ai;

import notai.client.ai.request.LlmTaskRequest;
import notai.client.ai.response.TaskResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import java.io.InputStream;

public interface AiClient {

    @PostExchange(url = "/api/ai/llm")
    TaskResponse submitLlmTask(@RequestBody LlmTaskRequest request);

    @PostExchange(url = "/api/ai/stt", contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
    TaskResponse submitSttTask(@RequestBody InputStream audioFileStream);
}
