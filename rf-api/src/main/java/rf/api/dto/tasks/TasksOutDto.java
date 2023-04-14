package rf.api.dto.tasks;

import rf.api.dto.BaseOutDto;

import java.util.*;

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
		private long id;
		private String name;
		private String command;
		/** key - parameter name, value - description */
		private Map<String, String> params;

		public Task(long id, String name, String command, Map<String, String> params) {
			this.id = id;
			this.name = name;
			this.command = command;
			this.params = Collections.unmodifiableMap(new LinkedHashMap<>(params));
		}

		public Task() {
			this(0, null, null, null);
		}

		public Long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		public String getCommand() {
			return command;
		}
		public void setCommand(String command) {
			this.command = command;
		}

		public Map<String, String> getParams() {
			return params;
		}
		public void setParams(Map<String, String> params) {
			this.params = params;
		}
	}
}
