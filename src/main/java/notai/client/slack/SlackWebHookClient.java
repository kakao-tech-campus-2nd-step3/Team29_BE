package notai.client.slack;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notai.common.exception.ErrorMessages;
import notai.common.exception.type.ExternalApiException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Async
@Slf4j
public class SlackWebHookClient {

    private final SlackWebHookProperty slackWebHookProperty;

    public void sendToInfoChannel(String message) {
        Slack slack = Slack.getInstance();
        Payload payload = Payload.builder().text(message).build();

        try {
            slack.send(slackWebHookProperty.infoChannel().webhookUrl(), payload);
        } catch (IOException e) {
            throw new ExternalApiException(ErrorMessages.SLACK_API_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public void sendToErrorChannel(String message) {
        Slack slack = Slack.getInstance();
        Payload payload = Payload.builder().text(message).build();

        try {
            slack.send(slackWebHookProperty.errorChannel().webhookUrl(), payload);
        } catch (IOException e) {
            throw new ExternalApiException(ErrorMessages.SLACK_API_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
