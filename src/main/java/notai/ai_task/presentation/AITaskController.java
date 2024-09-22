package notai.ai_task.presentation;

import lombok.RequiredArgsConstructor;
import notai.ai_task.application.AITaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/tasks")
@RequiredArgsConstructor
public class AITaskController {

    private final AITaskService aiTaskService;

}
