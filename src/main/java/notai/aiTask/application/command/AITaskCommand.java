package notai.aiTask.application.command;

import java.util.List;

public record AITaskCommand(
    Long documentId,
    List<Integer> pages
) {

}
