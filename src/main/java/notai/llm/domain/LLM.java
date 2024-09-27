package notai.llm.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;
import notai.problem.domain.Problem;
import notai.summary.domain.Summary;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;


/**
 * 요약과 문제 생성을 하는 LLM 모델의 작업 기록을 저장하는 테이블입니다.
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "llm")
public class LLM extends RootEntity<UUID> {

    @Id
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "summary_id")
    private Summary summary;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private TaskStatus status;

    public LLM(UUID id, Summary summary, Problem problem) {
        this.id = id;
        this.summary = summary;
        this.problem = problem;
        this.status = TaskStatus.PENDING;
    }

    public void completeTask() {
        this.status = TaskStatus.COMPLETED;
    }
}
