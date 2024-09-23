package notai.aiTask.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notai.aiTask.application.AITaskService;
import notai.aiTask.application.command.AITaskCommand;
import notai.aiTask.presentation.request.AITaskRequest;
import notai.aiTask.presentation.response.AITaskResponse;
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
