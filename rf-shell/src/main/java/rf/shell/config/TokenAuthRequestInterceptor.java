package rf.shell.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.shell.session.RfSession;

@Component
public class TokenAuthRequestInterceptor implements RequestInterceptor {
    private RfSession session;

    @Autowired
    public void setSessionManager(RfSession session) {
        this.session = session;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (session.isSignedIn()) {
            requestTemplate.header("Authorization", "Bearer " + session.getToken());
        }
    }
}
