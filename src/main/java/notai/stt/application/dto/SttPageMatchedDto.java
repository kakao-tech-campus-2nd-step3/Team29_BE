package notai.stt.application.dto;

import java.util.List;

public record SttPageMatchedDto(
        List<PageMatchedContent> pageContents
) {
    public record PageMatchedContent(
            Integer pageNumber,
            String content,
            List<PageMatchedWord> words
    ) {}

    public record PageMatchedWord(
            String word,
            Integer startTime,
            Integer endTime
    ) {}
}
