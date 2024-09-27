package notai.problem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;
import notai.document.domain.Document;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Problem extends RootEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @NotNull
    private Integer pageNumber;

    @Column(columnDefinition = "TEXT")
    private String content;

    public Problem(Document document, Integer pageNumber) {
        this.document = document;
        this.pageNumber = pageNumber;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
