package notai.common.exception;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    // annotation
    ANNOTATION_NOT_FOUND("주석을 찾을 수 없습니다."),

    // document
    DOCUMENT_NOT_FOUND("자료를 찾을 수 없습니다."),

    // ocr
    OCR_RESULT_NOT_FOUND("OCR 데이터를 찾을 수 없습니다."),
    OCR_TASK_ERROR("PDF 파일을 통해 OCR 작업을 수행하는데 실패했습니다."),

    // folder
    FOLDER_NOT_FOUND("폴더를 찾을 수 없습니다."),

    // llm task
    LLM_TASK_LOG_NOT_FOUND("AI 작업 기록을 찾을 수 없습니다."),
    LLM_TASK_RESULT_ERROR("AI 요약 및 문제 생성 중에 문제가 발생했습니다."),

    // problem
    PROBLEM_NOT_FOUND("문제 정보를 찾을 수 없습니다."),

    // summary
    SUMMARY_NOT_FOUND("요약 정보를 찾을 수 없습니다."),

    // member
    MEMBER_NOT_FOUND("회원 정보를 찾을 수 없습니다."),

    // recording
    RECORDING_NOT_FOUND("녹음 파일을 찾을 수 없습니다."),

    // external api call
    KAKAO_API_ERROR("카카오 API 호출에 예외가 발생했습니다."),
    AI_SERVER_ERROR("AI 서버 API 호출에 예외가 발생했습니다."),

    // auth
    INVALID_ACCESS_TOKEN("유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN("유요하지 않은 Refresh Token입니다."),
    EXPIRED_REFRESH_TOKEN("만료된 Refresh Token입니다."),
    INVALID_LOGIN_TYPE("지원하지 않는 소셜 로그인 타입입니다."),

    // json conversion
    JSON_CONVERSION_ERROR("JSON-객체 변환 중에 오류가 발생했습니다."),

    // etc
    INVALID_FILE_TYPE("지원하지 않는 파일 형식입니다."),
    FILE_NOT_FOUND("존재하지 않는 파일입니다."),
    FILE_SAVE_ERROR("파일을 저장하는 과정에서 오류가 발생했습니다."),
    INVALID_AUDIO_ENCODING("오디오 파일이 잘못되었습니다.")
    ;

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
