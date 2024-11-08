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
     * 3. 매칭 결과를 저장하고 테스크를 완료 처리
     */
    public void updateSttResult(UpdateSttResultCommand command) {
        SttTask sttTask = sttTaskRepository.getById(command.taskId());
        Stt stt = sttTask.getStt();
        Recording recording = stt.getRecording();

        List<PageRecording> pageRecordings = pageRecordingRepository.findAllByRecordingIdOrderByStartTime(recording.getId());

        SttPageMatchedDto matchedResult = stt.matchWordsWithPages(command.words(), pageRecordings);
        List<Stt> pageMatchedSttResults = Stt.createFromMatchedResult(recording, matchedResult);
        sttRepository.saveAll(pageMatchedSttResults);

        sttTask.complete();
        sttTaskRepository.save(sttTask);
    }
}
