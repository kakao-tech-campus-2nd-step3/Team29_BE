package notai.stt.application;

import lombok.RequiredArgsConstructor;
import notai.client.ai.AiClient;
import notai.client.ai.response.TaskResponse;
import static notai.common.exception.ErrorMessages.FILE_NOT_FOUND;
import static notai.common.exception.ErrorMessages.FILE_READ_ERROR;
import notai.common.exception.type.FileProcessException;
import notai.common.exception.type.NotFoundException;
import notai.llm.domain.TaskStatus;
import notai.recording.domain.Recording;
import notai.recording.domain.RecordingRepository;
import notai.stt.application.command.SttRequestCommand;
import notai.stt.domain.SttRepository;
import notai.sttTask.domain.SttTask;
import notai.sttTask.domain.SttTaskRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Transactional
@RequiredArgsConstructor
public class SttTaskService {
    private final AiClient aiClient;
    private final SttRepository sttRepository;
    private final SttTaskRepository sttTaskRepository;
    private final RecordingRepository recordingRepository;

    public void submitSttTask(SttRequestCommand command) {
        Recording recording = recordingRepository.getById(command.recordingId());
        File audioFile = validateAudioFile(command.audioFilePath());

        try {
            byte[] audioBytes = Files.readAllBytes(audioFile.toPath());

            ByteArrayResource resource = new ByteArrayResource(audioBytes) {
                @Override
                public String getFilename() {
                    return audioFile.getName();
                }
            };

            TaskResponse response = aiClient.submitSttTask(resource);
            createAndSaveSttTask(recording, response);
        } catch (IOException e) {
            throw new FileProcessException(FILE_READ_ERROR);
        }
    }

    private File validateAudioFile(String audioFilePath) {
        File audioFile = new File(audioFilePath);
        if (!audioFile.exists()) {
            throw new NotFoundException(FILE_NOT_FOUND);
        }
        return audioFile;
    }

    private void createAndSaveSttTask(Recording recording, TaskResponse response) {
        SttTask sttTask = new SttTask(response.taskId(), TaskStatus.PENDING, recording);
        sttTaskRepository.save(sttTask);
    }
}
