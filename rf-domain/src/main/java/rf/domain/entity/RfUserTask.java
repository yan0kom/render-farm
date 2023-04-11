package rf.domain.entity;

import rf.domain.entity.enums.RfUserTaskState;

public class RfUserTask extends EntityWithId<RfUserTask> {
	private final RfUser user;
	private final RfTask task;
	private final RfUserTaskState state;

	public RfUserTask(long id, RfUser user, RfTask task, RfUserTaskState state) {
		super(id);
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

	@Override
	public RfUserTask withId(long value) {
		return new RfUserTask(value, user, task, state);
	}
}
