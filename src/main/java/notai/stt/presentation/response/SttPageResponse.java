package notai.stt.presentation.response;

import notai.stt.domain.Stt;

import java.util.List;

public record SttPageResponse(
        Integer pageNumber,
        List<SttContent> contents
) {
    public static SttPageResponse of(Integer pageNumber, List<Stt> sttList) {
        List<SttContent> contents = sttList.stream()
                                           .map(SttContent::from)
                                           .toList();
        return new SttPageResponse(pageNumber, contents);
    }

    public record SttContent(
            String content,
            Integer startTime,
            Integer endTime
    ) {
        public static SttContent from(Stt stt) {
            return new SttContent(
                    stt.getContent(),
                    stt.getStartTime(),
                    stt.getEndTime()
            );
        }
    }
}
