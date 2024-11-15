package notai.sttTask.application;

import notai.client.ai.AiClient;
import notai.client.ai.response.TaskResponse;
import notai.common.exception.ErrorMessages;
import notai.common.exception.type.FileProcessException;
import notai.common.exception.type.NotFoundException;
import notai.llm.domain.TaskStatus;
import notai.recording.domain.Recording;
import notai.recording.domain.RecordingRepository;
import notai.stt.application.SttTaskService;
import notai.stt.application.command.SttRequestCommand;
import notai.sttTask.domain.SttTask;
import notai.sttTask.domain.SttTaskRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class SttTaskServiceTest {


    @InjectMocks
    private SttTaskService sttTaskService;

    @Mock
    private AiClient aiClient;

    @Mock
    private SttTaskRepository sttTaskRepository;

    @Mock
    private RecordingRepository recordingRepository;

    private String audioFilePath;
    private File testFile;
    private Long recordingId;
    private Recording recording;

    @BeforeEach
    void setUp() throws IOException {
        recordingId = 1L;
        audioFilePath = "src/test/resources/test-audio.mp3";
        testFile = new File(audioFilePath);
        testFile.getParentFile().mkdirs();
        testFile.createNewFile();
        
        recording = mock(Recording.class);
    }

    @AfterEach
    void tearDown() {
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void STT_테스크_제출_성공() {
        // given
        SttRequestCommand command = new SttRequestCommand(recordingId, audioFilePath);
        UUID taskId = UUID.randomUUID();
        TaskResponse taskResponse = new TaskResponse(taskId, "stt");

        given(recordingRepository.getById(recordingId)).willReturn(recording);
        given(aiClient.submitSttTask(any(ByteArrayResource.class))).willReturn(taskResponse);

        // when
        sttTaskService.submitSttTask(command);

        // then
        ArgumentCaptor<SttTask> sttTaskCaptor = ArgumentCaptor.forClass(SttTask.class);
        verify(sttTaskRepository).save(sttTaskCaptor.capture());

        SttTask savedTask = sttTaskCaptor.getValue();
        assertThat(savedTask.getId()).isEqualTo(taskId);
        assertThat(savedTask.getStatus()).isEqualTo(TaskStatus.PENDING);
        assertThat(savedTask.getRecording()).isEqualTo(recording);
    }

    @Test
    void STT_테스크_제출_실패_존재하지_않는_파일() {
        // given
        String nonExistentFilePath = "src/test/resources/존재하지않는파일.mp3";
        SttRequestCommand command = new SttRequestCommand(recordingId, nonExistentFilePath);

        given(recordingRepository.getById(recordingId)).willReturn(recording);

        // when & then
        assertThrows(NotFoundException.class, () -> sttTaskService.submitSttTask(command));
    }

    @Test
    void STT_테스크_제출_실패_파일_읽기_오류() {
        // given
        SttRequestCommand command = new SttRequestCommand(recordingId, audioFilePath);

        given(recordingRepository.getById(recordingId)).willReturn(recording);
        given(aiClient.submitSttTask(any(ByteArrayResource.class)))
                .willThrow(new FileProcessException(ErrorMessages.RECORDING_NOT_FOUND));

        // when & then
        assertThrows(FileProcessException.class, () -> sttTaskService.submitSttTask(command));
    }

    @Test
    void STT_테스크_제출_실패_존재하지_않는_녹음() {
        // given
        Long nonExistentRecordingId = 999L;
        SttRequestCommand command = new SttRequestCommand(nonExistentRecordingId, audioFilePath);

        given(recordingRepository.getById(nonExistentRecordingId))
                .willThrow(new NotFoundException(ErrorMessages.RECORDING_NOT_FOUND));

        // when & then
        assertThrows(NotFoundException.class, () -> sttTaskService.submitSttTask(command));
    }
}
