package notai.pageRecording.query;

import notai.pageRecording.domain.PageRecording;

import java.util.List;

public interface PageRecordingQueryRepository {

    List<PageRecording> findAllByRecordingIdOrderByStartTime(Long recordingId);
}
