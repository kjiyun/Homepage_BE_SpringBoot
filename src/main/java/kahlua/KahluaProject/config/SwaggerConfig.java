package kahlua.KahluaProject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@OpenAPIDefinition(
        info = @Info(title = "Kahluaband API 명세서",
                description = "Kahluaband 홈페이지 API 명세서",
                version = "v1"),
        servers = {@Server(url = "https://kahluaband.com/api", description = "https url"),
                @Server(url = "/", description = "Default Server URL")})
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    private final String SCHEME_NAME = "JWT Authentication";
    private final String BEARER_FORMAT = "JWT";
    private final String SCHEME = "bearer";
    private final String HEADER_NAME = "Authorization";

    @Bean
    public GroupedOpenApi chatOpenApi() {
        // "/v1/**" 경로에 매칭되는 API를 그룹화하여 문서화한다.
        String[] paths = {"/v1/**"};

        return GroupedOpenApi.builder()
                .group("Kahluaband API v1")  // 그룹 이름을 설정한다.
                .pathsToMatch(paths)     // 그룹에 속하는 경로 패턴을 지정한다.
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(SCHEME_NAME);
        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(new Components().addSecuritySchemes(SCHEME_NAME, createSecurityScheme()));
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name(HEADER_NAME)
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP)
                .scheme(SCHEME)
                .bearerFormat(BEARER_FORMAT);
    }
}