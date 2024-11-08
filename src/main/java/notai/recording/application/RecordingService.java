package notai.recording.application;

import lombok.RequiredArgsConstructor;
import notai.common.domain.vo.FilePath;
import static notai.common.exception.ErrorMessages.FILE_SAVE_ERROR;
import static notai.common.exception.ErrorMessages.INVALID_AUDIO_ENCODING;
import notai.common.exception.type.BadRequestException;
import notai.common.exception.type.InternalServerErrorException;
import notai.common.utils.AudioDecoder;
import notai.common.utils.FileManager;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import notai.member.domain.Member;
import notai.recording.application.command.RecordingSaveCommand;
import notai.recording.application.result.RecordingSaveResult;
import notai.recording.domain.Recording;
import notai.recording.domain.RecordingRepository;
import notai.stt.application.SttTaskService;
import notai.stt.application.command.SttRequestCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordingService {

    private final RecordingRepository recordingRepository;
    private final DocumentRepository documentRepository;
    private final AudioDecoder audioDecoder;
    private final FileManager fileManager;
    private final SttTaskService sttTaskService;

    @Value("${file.audio.basePath}")
    private String audioBasePath;

    public RecordingSaveResult saveRecording(Member member, RecordingSaveCommand command) {
        Document foundDocument = documentRepository.getById(command.documentId());
        foundDocument.validateOwner(member);

        Recording recording = new Recording(foundDocument);
        Recording savedRecording = recordingRepository.save(recording);

        FilePath filePath =
                FilePath.from(audioBasePath + foundDocument.getName() + "_" + savedRecording.getId() + ".mp3");

        try {
            byte[] binaryAudioData = audioDecoder.decode(command.audioData());
            Path outputPath = Paths.get(filePath.getFilePath());

            fileManager.save(binaryAudioData, outputPath);
            savedRecording.updateFilePath(filePath);

            SttRequestCommand sttCommand = new SttRequestCommand(savedRecording.getId(), filePath.getFilePath());
            sttTaskService.submitSttTask(sttCommand);

            return RecordingSaveResult.of(savedRecording.getId(), foundDocument.getId(), savedRecording.getCreatedAt());

        } catch (IllegalArgumentException e) {
            throw new BadRequestException(INVALID_AUDIO_ENCODING + " : " + e.getMessage());
        } catch (IOException e) {
            throw new InternalServerErrorException(FILE_SAVE_ERROR); // TODO: 재시도 로직 추가?
        }
    }
}
