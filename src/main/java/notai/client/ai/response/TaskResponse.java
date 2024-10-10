package notai.client.ai.response;

import java.util.UUID;

public record TaskResponse(
        UUID taskId,
        String taskType
) {
}
