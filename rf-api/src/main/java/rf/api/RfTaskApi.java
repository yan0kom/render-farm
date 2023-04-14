package rf.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import rf.api.dto.tasks.TasksOutDto;

public interface RfTaskApi {
	@GetMapping("/tasks")
	ResponseEntity<TasksOutDto> list();
}
