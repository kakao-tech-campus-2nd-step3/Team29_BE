package notai.pageRecording.domain;

import notai.pageRecording.query.PageRecordingQueryRepository;
import notai.recording.domain.Recording;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRecordingRepository extends 
        JpaRepository<PageRecording, Long>, PageRecordingQueryRepository {

    List<PageRecording> findAllByRecording(Recording recording);
}
