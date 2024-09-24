package notai.summary.domain;

import jakarta.persistence.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.validation.constraints.NotNull;
import static lombok.AccessLevel.PROTECTED;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;
import notai.document.domain.Document;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Summary extends RootEntity<Long> {

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
}
