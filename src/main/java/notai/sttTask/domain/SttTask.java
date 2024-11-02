package notai.sttTask.domain;

import static jakarta.persistence.CascadeType.PERSIST;
import jakarta.persistence.*;
import static jakarta.persistence.FetchType.LAZY;
import jakarta.validation.constraints.NotNull;
import static lombok.AccessLevel.PROTECTED;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;
import notai.llm.domain.TaskStatus;
import notai.stt.domain.Stt;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "stt_task")
public class SttTask extends RootEntity<UUID> {

    @Id
    private UUID id;

    @OneToOne(fetch = LAZY, cascade = PERSIST)
    @JoinColumn(name = "stt_id")
    private Stt stt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TaskStatus status;

    public SttTask(UUID id, Stt stt, TaskStatus status) {
        this.id = id;
        this.stt = stt;
        this.status = status;
    }

    public void complete() {
        this.status = TaskStatus.COMPLETED;
    }
}
