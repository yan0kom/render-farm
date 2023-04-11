package rf.domain.entity;

import java.util.Collections;
import java.util.Map;

public class RfTask extends EntityWithId<RfTask> {
	private final String name;
	private final String command;
	/** key - parameter name, value - description */
	private final Map<String, String> params;

	public RfTask(long id, String name, String command, Map<String, String> params) {
		super(id);
		this.name = name;
		this.command = command;
		this.params = Collections.unmodifiableMap(params);
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

	@Override
	public RfTask withId(long value) {
		return new RfTask(value, name, command, params);
	}

	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + ", command=" + command + ", params.size=" + params.size() + "]";
	}
}
