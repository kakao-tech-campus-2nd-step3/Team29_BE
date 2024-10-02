package notai.recording.application;

import lombok.RequiredArgsConstructor;
import notai.common.domain.vo.FilePath;
import notai.common.exception.type.BadRequestException;
import notai.common.exception.type.InternalServerErrorException;
import notai.common.exception.type.NotFoundException;
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
        // TODO: document 개발 코드 올려주시면, getById 로 수정
        Document foundDocument =
                documentRepository.findById(command.documentId()).orElseThrow(() -> new NotFoundException(""));

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
            throw new BadRequestException("오디오 파일이 잘못되었습니다.");
        } catch (IOException e) {
            throw new InternalServerErrorException("녹음 파일 저장 중 오류가 발생했습니다."); // TODO: 재시도 로직 추가?
        }
    }
}
