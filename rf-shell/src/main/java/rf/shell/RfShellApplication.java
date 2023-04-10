package rf.shell;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RfShellApplication {

	private static Logger log = LoggerFactory.getLogger(RfShellApplication.class);

	private List<String> args;

	public static void main(String[] args) {
		log.info("STARTING THE APPLICATION");
		SpringApplication.run(RfShellApplication.class, args);
		log.info("APPLICATION FINISHED");
	}

	public void run(String... argsArray) {
		log.info("EXECUTING : command line runner");

		for (int i = 0; i < argsArray.length; ++i) {
			log.info("args[{}]: {}", i, argsArray[i]);
		}

		args = List.of(argsArray);

	}
}
