package notai.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private final String serverUrl;

    public SwaggerConfig(@Value("${server-url}") String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme().name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .description("토큰값을 입력하여 인증을 활성화할 수 있습니다.")
                .bearerFormat("JWT"));
        Server server = new Server();
        server.setUrl(serverUrl);
        return new OpenAPI().components(new Components())
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components)
                .addServersItem(server);
    }

    private Info apiInfo() {
        return new Info().title("notai API").description("notai API 문서입니다.").version("0.0.1");
    }
}
