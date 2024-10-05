package notai.pageRecording.application;

import lombok.RequiredArgsConstructor;
import notai.common.exception.type.NotFoundException;
import notai.pageRecording.application.command.PageRecordingSaveCommand;
import notai.pageRecording.domain.PageRecording;
import notai.pageRecording.domain.PageRecordingRepository;
import notai.recording.domain.Recording;
import notai.recording.domain.RecordingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PageRecordingService {

    private final PageRecordingRepository pageRecordingRepository;
    private final RecordingRepository recordingRepository;

    public void savePageRecording(PageRecordingSaveCommand command) {
        Recording foundRecording = recordingRepository.getById(command.recordingId());
        checkDocumentOwnershipOfRecording(command, foundRecording);

        command.sessions().forEach(session -> {
            PageRecording pageRecording = new PageRecording(
                    foundRecording,
                    session.pageNumber(),
                    session.startTime(),
                    session.endTime()
            );
            pageRecordingRepository.save(pageRecording);
        });
    }

    private static void checkDocumentOwnershipOfRecording(PageRecordingSaveCommand command, Recording foundRecording) {
        if (!foundRecording.isRecordingOwnedByDocument(command.documentId())) {
            throw new NotFoundException("해당 녹음 파일을 찾을 수 없습니다.");
        }
    }
}
