package notai.client.ai;

import lombok.extern.slf4j.Slf4j;
import static notai.client.HttpInterfaceUtil.createHttpInterface;
import notai.common.exception.type.ExternalApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
public class AiClientConfig {

    @Value("${ai-server-url}")
    private String aiServerUrl;

    @Bean
    public AiClient aiClient() {
        RestClient restClient =
                RestClient.builder().baseUrl(aiServerUrl).requestInterceptor((request, body, execution) -> {
                    request.getHeaders().setContentLength(body.length); // Content-Length 설정 안하면 411 에러 발생
                    return execution.execute(request, body);
                }).defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    String responseBody = new String(response.getBody().readAllBytes());
                    log.error("Response Status: {}", response.getStatusCode());
                    throw new ExternalApiException(responseBody, response.getStatusCode().value());
                }).build();
        return createHttpInterface(restClient, AiClient.class);
    }
}
