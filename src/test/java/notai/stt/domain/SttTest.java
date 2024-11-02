package notai.stt.domain;

import notai.pageRecording.domain.PageRecording;
import notai.recording.domain.Recording;
import notai.stt.application.command.UpdateSttResultCommand;
import notai.stt.application.dto.SttPageMatchedDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class SttTest {

    @Test
    void 페이지_매칭_빈_페이지_리스트() {
        // given
        Recording recording = mock(Recording.class);
        Stt stt = new Stt(recording);
        List<UpdateSttResultCommand.Word> words = List.of(
                new UpdateSttResultCommand.Word("테스트", 1.0, 2.0)
        );

        // when
        SttPageMatchedDto result = stt.matchWordsWithPages(words, List.of());

        // then
        assertThat(result.pageContents()).isEmpty();
    }

    @Test
    void 페이지_매칭_정상_케이스() {
        // given
        Recording recording = mock(Recording.class);
        Stt stt = new Stt(recording);

        List<UpdateSttResultCommand.Word> words = List.of(
                new UpdateSttResultCommand.Word("첫번째", 1.0, 2.0),
                new UpdateSttResultCommand.Word("두번째", 2.5, 3.5),
                new UpdateSttResultCommand.Word("세번째", 4.0, 5.0)
        );


        List<PageRecording> pages = List.of(
                createPageRecording(1, 0.0, 3.0),
                createPageRecording(2, 3.0, 6.0)
        );

        // when
        SttPageMatchedDto result = stt.matchWordsWithPages(words, pages);

        // then
        assertAll(
                () -> assertThat(result.pageContents()).hasSize(2),
                () -> assertThat(result.pageContents().get(0).pageNumber()).isEqualTo(1),
                () -> assertThat(result.pageContents().get(0).content()).isEqualTo("첫번째 두번째"),
                () -> assertThat(result.pageContents().get(1).pageNumber()).isEqualTo(2),
                () -> assertThat(result.pageContents().get(1).content()).isEqualTo("세번째")
        );
    }

    @Test
    void 페이지_컨텐츠로부터_STT_엔티티_생성() {
        // given
        Recording recording = mock(Recording.class);
        List<SttPageMatchedDto.PageMatchedWord> words = List.of(
                new SttPageMatchedDto.PageMatchedWord("테스트", 100, 200),
                new SttPageMatchedDto.PageMatchedWord("단어", 300, 400)
        );
        SttPageMatchedDto.PageMatchedContent content = new SttPageMatchedDto.PageMatchedContent(
                1,
                "테스트 단어",
                words
        );

        // when
        Stt result = Stt.createFromPageContent(recording, content);

        // then
        assertAll(
                () -> assertThat(result.getPageNumber()).isEqualTo(1),
                () -> assertThat(result.getContent()).isEqualTo("테스트 단어"),
                () -> assertThat(result.getStartTime()).isEqualTo(100),
                () -> assertThat(result.getEndTime()).isEqualTo(400)
        );
    }

    @Test
    void 페이지_매칭_비순차적_페이지_번호() {
        // given
        Recording recording = mock(Recording.class);
        Stt stt = new Stt(recording);

        List<UpdateSttResultCommand.Word> words = List.of(
                new UpdateSttResultCommand.Word("word1", 1.0, 2.0),
                new UpdateSttResultCommand.Word("word2", 6.0, 7.0),
                new UpdateSttResultCommand.Word("word3", 8.0, 9.0),
                new UpdateSttResultCommand.Word("word4", 10.0, 11.0)
        );

        List<PageRecording> pages = List.of(
                createPageRecording(1, 0.0, 3.0),
                createPageRecording(5, 5.0, 7.0),
                createPageRecording(3, 7.0, 9.0),
                createPageRecording(4, 9.0, 12.0)
        );

        // when
        SttPageMatchedDto result = stt.matchWordsWithPages(words, pages);

        // then
        assertAll(
                () -> assertThat(result.pageContents()).hasSize(4),
                () -> assertThat(result.pageContents().get(0).pageNumber()).isEqualTo(1),
                () -> assertThat(result.pageContents().get(0).content()).isEqualTo("word1"),
                () -> assertThat(result.pageContents().get(1).pageNumber()).isEqualTo(3),
                () -> assertThat(result.pageContents().get(1).content()).isEqualTo("word3"),
                () -> assertThat(result.pageContents().get(2).pageNumber()).isEqualTo(4),
                () -> assertThat(result.pageContents().get(2).content()).isEqualTo("word4"),
                () -> assertThat(result.pageContents().get(3).pageNumber()).isEqualTo(5),
                () -> assertThat(result.pageContents().get(3).content()).isEqualTo("word2")
        );
    }

    @Test
    void 페이지_매칭_시간_경계값_테스트() {
        // given
        Recording recording = mock(Recording.class);
        Stt stt = new Stt(recording);

        List<UpdateSttResultCommand.Word> words = List.of(
                new UpdateSttResultCommand.Word("경계단어1", 2.99, 3.5),  // 첫 페이지 끝에 걸침
                new UpdateSttResultCommand.Word("경계단어2", 3.0, 3.8),   // 정확히 두번째 페이지 시작
                new UpdateSttResultCommand.Word("경계단어3", 5.01, 6.0),  // 두번째 페이지 끝에 걸침
                new UpdateSttResultCommand.Word("경계단어4", 5.51, 6.2)   // 벗어나 세번째 페이지로 분류
        );

        List<PageRecording> pages = List.of(
                createPageRecording(1, 0.0, 3.0),
                createPageRecording(2, 3.0, 5.5),
                createPageRecording(3, 5.5, 8.0)
        );

        // when
        SttPageMatchedDto result = stt.matchWordsWithPages(words, pages);

        // then
        assertAll(
                () -> assertThat(result.pageContents()).hasSize(3),
                () -> assertThat(result.pageContents().get(0).pageNumber()).isEqualTo(1),
                () -> assertThat(result.pageContents().get(0).content()).isEqualTo("경계단어1"),
                () -> assertThat(result.pageContents().get(1).pageNumber()).isEqualTo(2),
                () -> assertThat(result.pageContents().get(1).content()).isEqualTo("경계단어2 경계단어3"),
                () -> assertThat(result.pageContents().get(2).pageNumber()).isEqualTo(3),
                () -> assertThat(result.pageContents().get(2).content()).isEqualTo("경계단어4")
        );
    }

    @Test
    void 페이지_매칭_마지막_페이지_특수처리() {
        // given
        Recording recording = mock(Recording.class);
        Stt stt = new Stt(recording);

        List<UpdateSttResultCommand.Word> words = List.of(
                new UpdateSttResultCommand.Word("정상단어", 1.0, 2.0),
                new UpdateSttResultCommand.Word("늦은단어1", 7.0, 8.0),
                // 마지막 페이지 종료 시간 이후
                new UpdateSttResultCommand.Word("늦은단어2", 8.0, 9.0)
                // 마지막 페이지에 포함되어야 함
        );

        List<PageRecording> pages = List.of(createPageRecording(1, 0.0, 3.0), createPageRecording(2, 3.0, 6.0));

        // when
        SttPageMatchedDto result = stt.matchWordsWithPages(words, pages);

        // then
        assertAll(
                () -> assertThat(result.pageContents()).hasSize(2),
                () -> assertThat(result.pageContents().get(0).pageNumber()).isEqualTo(1),
                () -> assertThat(result.pageContents().get(0).content()).isEqualTo("정상단어"),
                () -> assertThat(result.pageContents().get(1).pageNumber()).isEqualTo(2),
                () -> assertThat(result.pageContents().get(1).content()).isEqualTo("늦은단어1 늦은단어2")
                // 마지막 페이지는 시간 제한 없이 모든 단어 포함
        );
    }

    @Test
    void 매칭_결과로부터_여러_STT_엔티티_생성() {
        // given
        Recording recording = mock(Recording.class);
        List<SttPageMatchedDto.PageMatchedContent> contents = List.of(
                new SttPageMatchedDto.PageMatchedContent(
                        1,
                        "첫번째 페이지",
                        List.of(
                                new SttPageMatchedDto.PageMatchedWord("첫번째", 1, 2),
                                new SttPageMatchedDto.PageMatchedWord("페이지", 2, 3)
                        )
                ),
                new SttPageMatchedDto.PageMatchedContent(
                        2,
                        "두번째 페이지",
                        List.of(
                                new SttPageMatchedDto.PageMatchedWord("두번째", 4, 5),
                                new SttPageMatchedDto.PageMatchedWord("페이지", 5, 6)
                        )
                )
        );
        SttPageMatchedDto matchedResult = new SttPageMatchedDto(contents);

        // when
        List<Stt> results = Stt.createFromMatchedResult(recording, matchedResult);

        // then
        assertAll(() -> assertThat(results).hasSize(2), () -> {
            Stt firstStt = results.get(0);
            assertThat(firstStt.getRecording()).isEqualTo(recording);
            assertThat(firstStt.getPageNumber()).isEqualTo(1);
            assertThat(firstStt.getContent()).isEqualTo("첫번째 페이지");
            assertThat(firstStt.getStartTime()).isEqualTo(1);
            assertThat(firstStt.getEndTime()).isEqualTo(3);
        }, () -> {
            Stt secondStt = results.get(1);
            assertThat(secondStt.getRecording()).isEqualTo(recording);
            assertThat(secondStt.getPageNumber()).isEqualTo(2);
            assertThat(secondStt.getContent()).isEqualTo("두번째 페이지");
            assertThat(secondStt.getStartTime()).isEqualTo(4);
            assertThat(secondStt.getEndTime()).isEqualTo(6);
        });
    }

    @Test
    void 매칭_결과가_비어있을때_빈_리스트_반환() {
        // given
        Recording recording = mock(Recording.class);
        SttPageMatchedDto matchedResult = new SttPageMatchedDto(List.of());

        // when
        List<Stt> results = Stt.createFromMatchedResult(recording, matchedResult);

        // then
        assertThat(results).isEmpty();
    }

    @Test
    void 페이지_매칭_결과_순서_보장() {
        // given
        Recording recording = mock(Recording.class);
        Stt stt = new Stt(recording);

        // startTime 기준으로 정렬된 words
        List<UpdateSttResultCommand.Word> words = List.of(
                new UpdateSttResultCommand.Word("1번", 1.0, 2.0),
                new UpdateSttResultCommand.Word("3번", 6.0, 7.0),
                new UpdateSttResultCommand.Word("5번", 10.0, 11.0)
        );

        // startTime 기준으로 정렬된 pages
        List<PageRecording> pages = List.of(
                createPageRecording(1, 0.0, 3.0),
                createPageRecording(5, 5.0, 8.0),
                createPageRecording(3, 9.0, 12.0)
        );

        // when
        SttPageMatchedDto result = stt.matchWordsWithPages(words, pages);

        // then
        assertThat(result.pageContents()).extracting(SttPageMatchedDto.PageMatchedContent::pageNumber)
                                         .containsExactly(1, 3, 5);  // 페이지 번호 순서 검증
    }

    private PageRecording createPageRecording(int pageNumber, double startTime, double endTime) {
        PageRecording pageRecording = mock(PageRecording.class);
        when(pageRecording.getPageNumber()).thenReturn(pageNumber);
        when(pageRecording.getStartTime()).thenReturn(startTime);
        when(pageRecording.getEndTime()).thenReturn(endTime);
        return pageRecording;
    }
}
