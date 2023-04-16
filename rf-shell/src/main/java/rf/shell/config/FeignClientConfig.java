package rf.shell.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    /*private final RfSessionManager sessionManager;

    public FeignClientConfig(RfSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }*/
/*
    @Bean
    public Collection<RequestInterceptor> tokenAuthRequestInterceptor(RfSessionManager sessionManager) {
        return Collections.singletonList(requestTemplate ->
            sessionManager.getSession().ifPresent(session ->
                requestTemplate.header("Authorization", "Bearer " + session.getToken())));
    }
*/
    /*@Bean
    ErrorDecoder errorDecoder() {
        return (String methodKey, Response response) -> null;
    }*/
}
