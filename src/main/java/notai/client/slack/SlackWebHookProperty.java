package notai.client.slack;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "slack")
public record SlackWebHookProperty(
        WebhookProperty infoChannel,
        WebhookProperty errorChannel
) {
    public record WebhookProperty(
            String webhookUrl
    ) {
    }
}
