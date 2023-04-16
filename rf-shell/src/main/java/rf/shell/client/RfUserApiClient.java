package rf.shell.client;

import org.springframework.cloud.openfeign.FeignClient;
import rf.api.RfUserApi;

@FeignClient(name = "RfUserApi", url = "${application.rf-back.url}")
public interface RfUserApiClient extends RfUserApi {
}
