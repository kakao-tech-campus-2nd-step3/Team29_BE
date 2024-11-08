package notai.client.ai;

import lombok.extern.slf4j.Slf4j;
import notai.common.exception.type.ExternalApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import static notai.client.HttpInterfaceUtil.createHttpInterface;
import static notai.common.exception.ErrorMessages.AI_SERVER_ERROR;

@Slf4j
@Configuration
public class AiClientConfig {

    @Value("${ai-server-url}")
    private String aiServerUrl;

    @Bean
    public AiClient aiClient() {
        RestClient restClient = RestClient.builder()
            .baseUrl(aiServerUrl)
            .messageConverters(converters -> {
                converters.addAll(new RestTemplate().getMessageConverters());
                converters.add(new FormHttpMessageConverter());
            })
            .requestInterceptor((request, body, execution) -> {
                request.getHeaders().setContentLength(body.length); // Content-Length 설정 안하면 411 에러 발생
                return execution.execute(request, body);
            })
            .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                String responseBody = new String(response.getBody().readAllBytes());
                log.error("AI 서버에서 오류가 발생했습니다. - Status: {}, Body: {}",
                    response.getStatusCode(), 
                    responseBody
                );
                throw new ExternalApiException(AI_SERVER_ERROR, response.getStatusCode().value());
            })
            .build();

        return createHttpInterface(restClient, AiClient.class);
    }
}
