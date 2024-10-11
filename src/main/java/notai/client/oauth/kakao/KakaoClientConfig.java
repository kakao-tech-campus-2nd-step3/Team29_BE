package notai.client.oauth.kakao;

import lombok.extern.slf4j.Slf4j;
import notai.common.exception.type.ExternalApiException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import static notai.client.HttpInterfaceUtil.createHttpInterface;
import static notai.common.exception.ErrorMessages.KAKAO_API_ERROR;

@Slf4j
@Configuration
public class KakaoClientConfig {

    @Bean
    public KakaoClient kakaoClient() {
        RestClient restClient = RestClient.builder().defaultStatusHandler(HttpStatusCode::isError,
                (request, response) -> {
                    String responseData = new String(response.getBody().readAllBytes());
                    log.error("카카오톡 API 오류 : {}", responseData);
                    throw new ExternalApiException(KAKAO_API_ERROR, response.getStatusCode().value());
                }
        ).build();
        return createHttpInterface(restClient, KakaoClient.class);
    }
}
