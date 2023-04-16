package rf.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication(scanBasePackages = {"rf.shell"})
@EnableFeignClients("rf.shell.client")
public class RfShellApplication {

	public static void main(String[] args) {
		System.out.println("Starting rf-shell...");
		SpringApplication.run(RfShellApplication.class);
	}

	@Bean
	public PromptProvider myPromptProvider() {
		return () -> new AttributedString("rf-shell: ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
	}
}
