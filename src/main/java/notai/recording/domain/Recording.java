package notai.recording.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;
import notai.common.domain.vo.FilePath;
import notai.document.domain.Document;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Recording extends RootEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @Embedded
    private FilePath filePath;

    public Recording(Document document) {
        this.document = document;
    }

    public void updateFilePath(FilePath filePath) {
        this.filePath = filePath;
    }

    public boolean isRecordingOwnedByDocument(Long documentId) {
        return this.document.getId().equals(documentId);
    }
}
