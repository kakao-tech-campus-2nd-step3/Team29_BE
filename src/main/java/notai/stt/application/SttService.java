package notai.stt.application;

import lombok.RequiredArgsConstructor;
import notai.pageRecording.domain.PageRecording;
import notai.pageRecording.domain.PageRecordingRepository;
import notai.recording.domain.Recording;
import notai.stt.application.command.UpdateSttResultCommand;
import notai.stt.application.dto.SttPageMatchedDto;
import notai.stt.domain.Stt;
import notai.stt.domain.SttRepository;
import notai.sttTask.domain.SttTask;
import notai.sttTask.domain.SttTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SttService {
    private final SttRepository sttRepository;
    private final SttTaskRepository sttTaskRepository;
    private final PageRecordingRepository pageRecordingRepository;

    /**
     * AI 서버로부터 받은 STT 결과를 처리하여 페이지별 STT 데이터를 생성하고 저장합니다.
     * 1. STT 테스크와 관련 엔티티들을 조회
     * 2. 음성 인식된 단어들을 페이지와 매칭
     * 3. 테스크를 완료처리하고 매칭 결과 저장
     */
    public void updateSttResult(UpdateSttResultCommand command) {
        SttTask sttTask = sttTaskRepository.getById(command.taskId());
        Recording recording = sttTask.getRecording();

        List<PageRecording> pageRecordings =
                pageRecordingRepository.findAllByRecordingIdOrderByStartTime(recording.getId());

        SttPageMatchedDto matchedResult = Stt.matchWordsWithPages(command.words(), pageRecordings);
        List<Stt> pageMatchedSttResults = Stt.createFromMatchedResult(sttTask, matchedResult);

        sttTask.complete();
        sttTaskRepository.save(sttTask);

        sttRepository.saveAll(pageMatchedSttResults);
    }
}
