package rf.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"rf.api", "rf.domain", "rf.back", "rf.repo"})
public class RfBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(RfBackApplication.class, args);
	}

}
