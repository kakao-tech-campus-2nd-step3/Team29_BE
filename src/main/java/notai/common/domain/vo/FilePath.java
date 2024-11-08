package notai.common.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.exception.type.BadRequestException;

import static lombok.AccessLevel.PROTECTED;
import static notai.common.exception.ErrorMessages.INVALID_FILE_TYPE;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class FilePath {

    @Column(length = 255)
    private String filePath;

    private FilePath(String filePath) {
        this.filePath = filePath;
    }

    public static FilePath from(String filePath) {
        return new FilePath(filePath);
    }
}
