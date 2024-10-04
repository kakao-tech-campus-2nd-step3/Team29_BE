package notai.common.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.exception.type.BadRequestException;

import static lombok.AccessLevel.PROTECTED;

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
            throw new BadRequestException("지원하지 않는 파일 형식입니다.");
        }
        return new FilePath(filePath);
    }
}
