package notai.summary.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Summary extends RootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long documentId;

    @NotNull
    private Integer pageNumber;

    @Column(columnDefinition = "TEXT")
    private String content;
}
