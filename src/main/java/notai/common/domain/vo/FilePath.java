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

    @Column(length = 50)
    private String filePath;

    private FilePath(String filePath) {
        this.filePath = filePath;
    }

    public static FilePath from(String filePath) {
        // 추후 확장자 추가
        if (!filePath.matches(
                "[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣()\\[\\]+\\-&/_\\s]+(/[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣()\\[\\]+\\-&/_\\s]+)*/?[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣()\\[\\]+\\-&/_\\s]+\\.mp3")) {
            throw new BadRequestException(INVALID_FILE_TYPE);
        }
        return new FilePath(filePath);
    }
}
