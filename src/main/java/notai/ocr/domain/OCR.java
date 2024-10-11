package notai.ocr.domain;

import jakarta.persistence.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.validation.constraints.NotNull;
import static lombok.AccessLevel.PROTECTED;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;
import notai.document.domain.Document;

@Entity
@Table(name = "ocr")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class OCR extends RootEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;

    @NotNull
    @Column(name = "page_number")
    private Integer pageNumber;

    @NotNull
    @Column(name = "content", length = 255)
    private String content;

    public OCR(Document document, Integer pageNumber, String content) {
        this.document = document;
        this.pageNumber = pageNumber;
        this.content = content;
    }
}
