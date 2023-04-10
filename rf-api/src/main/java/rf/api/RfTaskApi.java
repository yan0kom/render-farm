package rf.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import rf.api.dto.tasks.TasksOutDto;

@RequestMapping("/api/tasks")
public interface RfTaskApi {
	@GetMapping
	ResponseEntity<TasksOutDto> list();
}
