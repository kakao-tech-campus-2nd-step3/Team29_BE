package notai.sttTask.domain;

import jakarta.persistence.*;
import static jakarta.persistence.FetchType.LAZY;
import jakarta.validation.constraints.NotNull;
import static lombok.AccessLevel.PROTECTED;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;
import notai.llm.domain.TaskStatus;
import notai.recording.domain.Recording;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "stt_task")
public class SttTask extends RootEntity<UUID> {

    @Id
    private UUID id;

    @NotNull
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "recording_id")
    private Recording recording;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TaskStatus status;

    public SttTask(UUID id, TaskStatus status, Recording recording) {
        this.id = id;
        this.status = status;
        this.recording = recording;
    }

    public void complete() {
        this.status = TaskStatus.COMPLETED;
    }
}
