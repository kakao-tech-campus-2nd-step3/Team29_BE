package notai.pageRecording.presentation.request;

import notai.pageRecording.application.command.PageRecordingSaveCommand;

import java.util.List;
import java.util.stream.IntStream;

public record PageRecordingSaveRequest(
        Long recordingId,
        List<PageTurnEvent> events
) {
    public PageRecordingSaveCommand toCommand(Long documentId) {
        return new PageRecordingSaveCommand(
                documentId,
                recordingId,
                IntStream.range(0, events.size())
                         .mapToObj(i -> new PageRecordingSaveCommand.PageRecordingSession(
                                 events.get(i).nextPage(),
                                 events.get(i).timestamp(), // 페이지 넘김 이벤트가 발생했을 때의 시간을 페이지별 녹음의 시작 시간으로 둡니다.
                                 (i < events.size() - 1) ? events.get(i + 1).timestamp() : null // 다음 페이지 넘김 이벤트가 발생했을 때의 시간을 끝 시간으로 둡니다. 마지막 페이지의 끝 시간은 null 입니다.
                         ))
                         .toList()
        );
    }

    public record PageTurnEvent(
            Integer prevPage,
            Integer nextPage,
            Double timestamp
    ) {
    }
}
