package notai.stt.application;

import static notai.common.exception.ErrorMessages.AI_SERVER_ERROR;
import notai.common.exception.type.NotFoundException;
import notai.pageRecording.domain.PageRecording;
import notai.pageRecording.domain.PageRecordingRepository;
import notai.recording.domain.Recording;
import notai.stt.application.command.UpdateSttResultCommand;
import notai.stt.application.dto.SttPageMatchedDto;
import notai.stt.domain.Stt;
import notai.stt.domain.SttRepository;
import notai.sttTask.domain.SttTask;
import notai.sttTask.domain.SttTaskRepository;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class SttServiceTest {

    @InjectMocks
    private SttService sttService;

    @Mock
    private SttRepository sttRepository;

    @Mock
    private SttTaskRepository sttTaskRepository;

    @Mock
    private PageRecordingRepository pageRecordingRepository;

    @Test
    void STT_결과_업데이트_성공() {
        // given
        UUID taskId = UUID.randomUUID();
        List<UpdateSttResultCommand.Word> words = List.of(
                new UpdateSttResultCommand.Word("테스트", 1.0, 2.0),
                new UpdateSttResultCommand.Word("음성인식", 3.1, 4)
        );
        UpdateSttResultCommand command = new UpdateSttResultCommand(taskId, words);

        SttTask sttTask = mock(SttTask.class);
        Stt stt = mock(Stt.class);
        Recording recording = mock(Recording.class);
        List<PageRecording> pageRecordings = List.of(mock(PageRecording.class));

        when(sttTaskRepository.getById(taskId)).thenReturn(sttTask);
        when(sttTask.getStt()).thenReturn(stt);
        when(stt.getRecording()).thenReturn(recording);
        when(recording.getId()).thenReturn(1L);
        when(pageRecordingRepository.findAllByRecordingIdOrderByStartTime(1L)).thenReturn(pageRecordings);

        List<SttPageMatchedDto.PageMatchedWord> matchedWords = List.of(
                new SttPageMatchedDto.PageMatchedWord("테스트", 1, 2),
                new SttPageMatchedDto.PageMatchedWord("음성인식", 3, 4)
        );
        SttPageMatchedDto matchedResult = new SttPageMatchedDto(List.of(
                new SttPageMatchedDto.PageMatchedContent(1, "테스트 음성인식", matchedWords)));
        when(stt.matchWordsWithPages(words, pageRecordings)).thenReturn(matchedResult);

        // when
        sttService.updateSttResult(command);

        // then
        verify(sttTask).complete();
        verify(sttTaskRepository).save(sttTask);
        verify(sttRepository).saveAll(argThat(sttList -> {
            List<Stt> results = (List<Stt>) sttList;
            return results.size() == 1;  // 예상되는 STT 엔티티 개수 확인
        }));
    }

    @Test
    void STT_결과_업데이트_실패_태스크_없음() {
        // given
        UUID taskId = UUID.randomUUID();
        List<UpdateSttResultCommand.Word> words = List.of(new UpdateSttResultCommand.Word("테스트", 1.0, 2.0));
        UpdateSttResultCommand command = new UpdateSttResultCommand(taskId, words);

        when(sttTaskRepository.getById(taskId)).thenThrow(new NotFoundException(AI_SERVER_ERROR));

        // when & then
        assertThrows(NotFoundException.class, () -> sttService.updateSttResult(command));
        verify(sttRepository, never()).saveAll(any());
    }

    @Test
    void STT_결과_업데이트_빈_페이지_리스트() {
        // given
        UUID taskId = UUID.randomUUID();
        List<UpdateSttResultCommand.Word> words = List.of(new UpdateSttResultCommand.Word("테스트", 1.0, 2.0));

        UpdateSttResultCommand command = new UpdateSttResultCommand(taskId, words);

        SttTask sttTask = mock(SttTask.class);
        Stt stt = mock(Stt.class);
        Recording recording = mock(Recording.class);

        when(sttTaskRepository.getById(taskId)).thenReturn(sttTask);
        when(sttTask.getStt()).thenReturn(stt);
        when(stt.getRecording()).thenReturn(recording);
        when(recording.getId()).thenReturn(1L);
        when(pageRecordingRepository.findAllByRecordingIdOrderByStartTime(1L)).thenReturn(List.of());

        SttPageMatchedDto matchedResult = new SttPageMatchedDto(List.of());
        when(stt.matchWordsWithPages(words, List.of())).thenReturn(matchedResult);

        // when
        sttService.updateSttResult(command);

        // then
        verify(sttTask).complete();
        verify(sttTaskRepository).save(sttTask);
        verify(sttRepository).saveAll(argThat(sttList -> ((List<Stt>) sttList).isEmpty()));
    }
}
