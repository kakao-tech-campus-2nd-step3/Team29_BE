package notai.client.ai;

import notai.client.ai.request.LlmTaskRequest;
import notai.client.ai.response.TaskResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

class AiClientTest {

    @Mock
    private AiClient aiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void LLM_테스크_전달_테스트() {
        // Given
        LlmTaskRequest request = LlmTaskRequest.of("OCR 텍스트", "STT 텍스트", "키보드 노트");
        UUID expectedTaskId = UUID.randomUUID();
        TaskResponse expectedResponse = new TaskResponse(expectedTaskId, "llm");
        when(aiClient.submitLlmTask(request)).thenReturn(expectedResponse);

        // When
        TaskResponse response = aiClient.submitLlmTask(request);

        // Then
        assertEquals(expectedResponse, response);
        verify(aiClient, times(1)).submitLlmTask(request);
    }

    @Test
    void STT_테스크_전달_테스트() {
        // Given
        MultipartFile mockAudioFile = mock(MultipartFile.class);
        UUID expectedTaskId = UUID.randomUUID();
        TaskResponse expectedResponse = new TaskResponse(expectedTaskId, "stt");
        when(aiClient.submitSttTask(mockAudioFile)).thenReturn(expectedResponse);

        // When
        TaskResponse response = aiClient.submitSttTask(mockAudioFile);

        // Then
        assertEquals(expectedResponse, response);
        verify(aiClient, times(1)).submitSttTask(mockAudioFile);
    }
}
