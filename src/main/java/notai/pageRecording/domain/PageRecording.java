package notai.pageRecording.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.recording.domain.Recording;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "page_recording")
public class PageRecording {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recording_id")
    private Recording recording;

    @NotNull
    private Integer pageNumber;

    @NotNull
    private Double startTime;

    @NotNull
    private Double endTime;

    public PageRecording(Recording recording, Integer pageNumber, Double startTime, Double endTime) {
        this.recording = recording;
        this.pageNumber = pageNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
