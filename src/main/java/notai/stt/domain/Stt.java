package notai.stt.domain;

import jakarta.persistence.*;
import static jakarta.persistence.FetchType.LAZY;
import jakarta.validation.constraints.NotNull;
import static lombok.AccessLevel.PROTECTED;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;
import notai.pageRecording.domain.PageRecording;
import notai.stt.application.command.UpdateSttResultCommand;
import notai.stt.application.dto.SttPageMatchedDto;
import notai.sttTask.domain.SttTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "stt")
public class Stt extends RootEntity<Long> {

    // Todo: 실제 테스트해보며 오차 시간 조정
    // 페이지 매칭 시 허용되는 시간 오차 (초)
    private static final double TIME_THRESHOLD = 0.0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stt_task_id")
    private SttTask sttTask;

    private Integer pageNumber;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer startTime;

    private Integer endTime;

    public Stt(SttTask sttTask) {
        this.sttTask = sttTask;
    }

    public Stt(SttTask sttTask, Integer pageNumber, String content, Integer startTime, Integer endTime) {
        this.sttTask = sttTask;
        this.pageNumber = pageNumber;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * 페이지별 STT 결과로부터 새로운 STT 엔티티를 생성합니다.
     * 시작/종료 시간은 페이지 내 첫/마지막 단어의 시간으로 설정합니다.
     */
    public static Stt createFromPageContent(SttTask sttTask, SttPageMatchedDto.PageMatchedContent content) {
        return new Stt(
                sttTask,
                content.pageNumber(),
                content.content(),
                content.words().get(0).startTime(),
                content.words().get(content.words().size() - 1).endTime()
        );
    }

    /**
     * 음성 인식된 단어들을 페이지 기록과 매칭하여 페이지별 STT 결과를 생성합니다.
     */
    public static SttPageMatchedDto matchWordsWithPages(
            List<UpdateSttResultCommand.Word> words,
            List<PageRecording> pageRecordings
    ) {
        if (pageRecordings.isEmpty()) {
            return new SttPageMatchedDto(List.of());
        }

        // 페이지 번호 순으로 자동 정렬됨
        Map<Integer, List<SttPageMatchedDto.PageMatchedWord>> pageWordMap = new TreeMap<>();
        int wordIndex = 0;
        PageRecording lastPage = pageRecordings.get(pageRecordings.size() - 1);

        // 각 페이지별로 매칭되는 단어들을 찾아 처리
        for (PageRecording page : pageRecordings) {
            List<SttPageMatchedDto.PageMatchedWord> pageWords = new ArrayList<>();
            double pageStart = page.getStartTime();
            Double pageEnd = page.getEndTime();

            // 현재 페이지의 시간 범위에 속하는 단어들을 찾아 매칭
            while (wordIndex < words.size()) {
                UpdateSttResultCommand.Word word = words.get(wordIndex);

                // 페이지 시작 시간보다 이른 단어는 건너뛰기
                if (word.start() + TIME_THRESHOLD < pageStart) {
                    wordIndex++;
                    continue;
                }

                // 마지막 페이지이거나 endTime이 null이면 시작 시간만 체크
                if ((page == lastPage || pageEnd == null) || word.start() - TIME_THRESHOLD < pageEnd) {
                    pageWords.add(new SttPageMatchedDto.PageMatchedWord(
                            word.word(),
                            (int) word.start(),
                            (int) word.end()
                    ));
                    wordIndex++;
                } else {
                    break;
                }
            }

            // 매칭된 단어가 있는 경우만 맵에 추가
            if (!pageWords.isEmpty()) {
                pageWordMap.put(page.getPageNumber(), pageWords);
            }
        }

        // 페이지별로 단어들을 하나의 텍스트로 합치는 과정
        List<SttPageMatchedDto.PageMatchedContent> pageContents = pageWordMap
                .entrySet().stream()
                .map(entry -> {
                    Integer pageNumber = entry.getKey();
                    List<SttPageMatchedDto.PageMatchedWord>
                            pageWords = entry.getValue();
                    String combinedContent =
                            pageWords.stream()
                                     .map(SttPageMatchedDto.PageMatchedWord::word)
                                     .collect(Collectors.joining(
                                             " "));
                    return new SttPageMatchedDto.PageMatchedContent(
                            pageNumber,
                            combinedContent,
                            pageWords
                    );
                })
                .toList();

        return new SttPageMatchedDto(pageContents);
    }

    /**
     * 페이지 매칭 결과로부터 STT 엔티티들을 생성하고 저장합니다.
     */
    public static List<Stt> createFromMatchedResult(SttTask sttTask, SttPageMatchedDto matchedResult) {
        return matchedResult.pageContents().stream()
                            .map(content -> createFromPageContent(sttTask, content))
                            .toList();
    }
}
