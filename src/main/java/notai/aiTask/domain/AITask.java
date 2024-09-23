package notai.aiTask.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;
import notai.problem.domain.Problem;
import notai.summary.domain.Summary;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "ai_task")
public class AITask extends RootEntity<UUID> {

    @Id
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "summary_id")
    private Summary summary;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private TaskStatus status;

    public AITask(UUID id, Summary summary, Problem problem) {
        this.id = id;
        this.summary = summary;
        this.problem = problem;
        this.status = TaskStatus.PENDING;
    }
}
