package notai.pageRecording.application;

import notai.pageRecording.application.command.PageRecordingSaveCommand;
import notai.pageRecording.application.command.PageRecordingSaveCommand.PageRecordingSession;
import notai.pageRecording.domain.PageRecording;
import notai.pageRecording.domain.PageRecordingRepository;
import notai.recording.domain.Recording;
import notai.recording.domain.RecordingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PageRecordingServiceTest {

    @InjectMocks
    private PageRecordingService pageRecordingService;

    @Mock
    private PageRecordingRepository pageRecordingRepository;

    @Mock
    private RecordingRepository recordingRepository;

    @Test
    void 페이지_넘김_이벤트에_따라_페이지별_녹음_시간을_저장() {
        // given
        Long recordingId = 1L;
        Long documentId = 1L;

        PageRecordingSaveCommand command = new PageRecordingSaveCommand(
                recordingId,
                documentId,
                List.of(new PageRecordingSession(1, 100.0, 185.5), new PageRecordingSession(5, 185.5, 290.3))
        );

        Recording foundRecording = mock(Recording.class);
        given(recordingRepository.getById(recordingId)).willReturn(foundRecording);
        given(foundRecording.isRecordingOwnedByDocument(documentId)).willReturn(true);

        // when
        pageRecordingService.savePageRecording(command);

        // then
        verify(pageRecordingRepository, times(2)).save(any(PageRecording.class));
    }
}