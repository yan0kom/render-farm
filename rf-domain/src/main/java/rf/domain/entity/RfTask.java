package rf.domain.entity;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class RfTask {
	private final Long id;
	private final String name;
	private final String command;
	/** key - parameter name, value - description */
	private final Map<String, String> params;

	public RfTask(Long id, String name, String command, Map<String, String> params) {
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

	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + ", command=" + command + ", params.size=" + params.size() + "]";
	}
}
