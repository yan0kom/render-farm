package rf.domain.entity;

import rf.domain.entity.enums.RfUserTaskState;

public class RfUserTask {
	private final RfUser user;
	private final RfTask task;
	private final RfUserTaskState state;

	public RfUserTask(RfUser user, RfTask task, RfUserTaskState state) {
		super();
		this.user = user;
		this.task = task;
		this.state = state;
	}

	public RfUser getUser() {
		return user;
	}
	public RfTask getTask() {
		return task;
	}
	public RfUserTaskState getState() {
		return state;
	}
}
