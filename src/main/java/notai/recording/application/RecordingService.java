package notai.recording.application;

import lombok.RequiredArgsConstructor;
import notai.common.domain.vo.FilePath;
import notai.common.exception.type.BadRequestException;
import notai.common.exception.type.InternalServerErrorException;
import notai.common.utils.AudioDecoder;
import notai.common.utils.FileManager;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import notai.recording.application.command.RecordingSaveCommand;
import notai.recording.application.result.RecordingSaveResult;
import notai.recording.domain.Recording;
import notai.recording.domain.RecordingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static notai.common.exception.ErrorMessages.FILE_SAVE_ERROR;
import static notai.common.exception.ErrorMessages.INVALID_AUDIO_ENCODING;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordingService {

    private final RecordingRepository recordingRepository;
    private final DocumentRepository documentRepository;
    private final AudioDecoder audioDecoder;
    private final FileManager fileManager;

    @Value("${file.audio.basePath}")
    private String audioBasePath;

    public RecordingSaveResult saveRecording(RecordingSaveCommand command) {
        Document foundDocument = documentRepository.getById(command.documentId());

        Recording recording = new Recording(foundDocument);
        Recording savedRecording = recordingRepository.save(recording);

        FilePath filePath =
                FilePath.from(audioBasePath + foundDocument.getName() + "_" + savedRecording.getId() + ".mp3");

        try {
            byte[] binaryAudioData = audioDecoder.decode(command.audioData());
            Path outputPath = Paths.get(filePath.getFilePath());

            fileManager.save(binaryAudioData, outputPath);
            savedRecording.updateFilePath(filePath);

            return RecordingSaveResult.of(savedRecording.getId(), foundDocument.getId(), savedRecording.getCreatedAt());

        } catch (IllegalArgumentException e) {
            throw new BadRequestException(INVALID_AUDIO_ENCODING);
        } catch (IOException e) {
            throw new InternalServerErrorException(FILE_SAVE_ERROR); // TODO: 재시도 로직 추가?
        }
    }
}
