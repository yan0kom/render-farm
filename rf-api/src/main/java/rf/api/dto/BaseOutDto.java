package rf.api.dto;

public class BaseOutDto {
	private final Boolean success;

	public BaseOutDto(Boolean success) {
		this.success = success;
	}

	public Boolean getSuccess() {
		return success;
	}
}
