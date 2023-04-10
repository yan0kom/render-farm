package rf.back.api.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import rf.api.RfTaskApi;
import rf.api.dto.tasks.TasksOutDto;
import rf.domain.service.RfTaskService;

@RestController
class RfTaskApiImpl implements RfTaskApi {
	private final RfTaskService rfTaskService;

	public RfTaskApiImpl(RfTaskService rfTaskService) {
		this.rfTaskService = rfTaskService;
	}

	@Override
	public ResponseEntity<TasksOutDto> list() {
    	var dto = new TasksOutDto();
    	rfTaskService.getAllTasks()
    			.map(rfTask -> new TasksOutDto.Task(
    					rfTask.getId(),
    					rfTask.getName(),
    					rfTask.getCommand(),
    					rfTask.getParams()))
    			.forEach(dto::addTask);
        return ResponseEntity.ok(dto);
	}
}
