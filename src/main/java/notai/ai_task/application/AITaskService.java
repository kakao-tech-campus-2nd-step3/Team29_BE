package notai.ai_task.application;

import lombok.RequiredArgsConstructor;
import notai.ai_task.domain.AITaskRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AITaskService {

    private final AITaskRepository aiTaskRepository;

}
