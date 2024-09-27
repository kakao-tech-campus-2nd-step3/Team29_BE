package notai.annotation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;
import notai.document.domain.Document;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "annotation")
public class Annotation extends RootEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id")
    @NotNull
    private Document document;

    @NotNull
    private int pageNumber;

    @NotNull
    private int x;

    @NotNull
    private int y;

    @NotNull
    private int width;

    @NotNull
    private int height;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String content;

    @Override
    public Long getId() {
        return this.id;
    }

    public Annotation(Document document, int pageNumber, int x, int y, int width, int height, String content) {
        this.document = document;
        this.pageNumber = pageNumber;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.content = content;
    }

    public void updateAnnotation(int x, int y, int width, int height, String content) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.content = content;
    }
}
