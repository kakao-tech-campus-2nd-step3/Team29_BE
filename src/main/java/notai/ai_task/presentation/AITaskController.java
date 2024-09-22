package notai.ai_task.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notai.ai_task.application.AITaskService;
import notai.ai_task.application.command.AITaskCommand;
import notai.ai_task.presentation.request.AITaskRequest;
import notai.ai_task.presentation.response.AITaskResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/tasks")
@RequiredArgsConstructor
public class AITaskController {

    private final AITaskService aiTaskService;

    @PostMapping
    public ResponseEntity<AITaskResponse> submitTask(@RequestBody @Valid AITaskRequest request) {
        AITaskCommand command = request.toCommand();
        AITaskResponse response = aiTaskService.submitTask(command);
        return ResponseEntity.accepted().body(response);
    }
}
