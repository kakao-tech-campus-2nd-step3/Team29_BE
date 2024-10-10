package notai.client.ai;

import notai.client.ai.request.LlmTaskRequest;
import notai.client.ai.response.TaskResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

class AiClientTest {

    @Mock
    private AiClient mockAiClient;

    @InjectMocks
    private AiClientImpl aiClientImpl;

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
        when(mockAiClient.submitLlmTask(request)).thenReturn(expectedResponse);

        // When
        TaskResponse response = aiClientImpl.submitLlmTask(request);

        // Then
        assertEquals(expectedResponse, response);
        verify(mockAiClient, times(1)).submitLlmTask(request);
    }

    @Test
    void testSubmitSttTask() {
        // Given
        MultipartFile mockAudioFile = mock(MultipartFile.class);
        UUID expectedTaskId = UUID.randomUUID();
        TaskResponse expectedResponse = new TaskResponse(expectedTaskId, "stt");
        when(mockAiClient.submitSttTask(mockAudioFile)).thenReturn(expectedResponse);

        // When
        TaskResponse response = aiClientImpl.submitSttTask(mockAudioFile);

        // Then
        assertEquals(expectedResponse, response);
        verify(mockAiClient, times(1)).submitSttTask(mockAudioFile);
    }
}
