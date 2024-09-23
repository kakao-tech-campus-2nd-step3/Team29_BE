package notai.aiTask.application;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import notai.aiTask.application.command.AITaskCommand;
import notai.aiTask.domain.AITaskRepository;
import notai.aiTask.presentation.response.AITaskResponse;
import org.springframework.stereotype.Service;

/**
 * SummaryService 와 ExamService 는 엔티티와 관련된 로직만 처리하고
 * AI 요약 및 문제 생성 요청은 여기서 처리하는 식으로 생각했습니다.
 * AI 서버와의 통신은 별도 클래스에서 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class AITaskService {

    private final AITaskRepository aiTaskRepository;

    /**
     * 흐름 파악용 임시 메서드
     */
    public AITaskResponse submitTask(AITaskCommand command) {
        command.pages().forEach(page -> {
            UUID taskId = sendRequestToAIServer();
            // TODO: command 데이터를 이용해 content 만 null 인 Summary, Problem 생성
            // TODO: Summary, Problem 과 매핑된 AITask 생성 -> 작업 상태는 모두 PENDING
        });

        return AITaskResponse.of(command.documentId(), LocalDateTime.now());
    }

    private UUID sendRequestToAIServer() {
        return UUID.randomUUID(); // 임시 값, 실제 구현에선 AI 서버에서 UUID 가 반환됨.
    }
}
