package rf.api.dto.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rf.api.dto.BaseOutDto;

public class TasksOutDto extends BaseOutDto {
	private final List<TasksOutDto.Task> tasks = new ArrayList<>();

	public TasksOutDto() {
		super(true);
	}

	public List<TasksOutDto.Task> getTasks() {
		return Collections.unmodifiableList(tasks);
	}

	public void addTask(TasksOutDto.Task task) {
		tasks.add(task);
	}

	public static class Task {
		private final Long id;
		private final String name;
		private final String command;
		/** key - parameter name, value - description */
		private final Map<String, String> params;

		public Task(Long id, String name, String command, Map<String, String> params) {
			this.id = id;
			this.name = name;
			this.command = command;
			this.params = Collections.unmodifiableMap(new LinkedHashMap<>(params));
		}

		public Long getId() {
			return id;
		}
		public String getName() {
			return name;
		}
		public String getCommand() {
			return command;
		}
		public Map<String, String> getParams() {
			return params;
		}
	}
}
