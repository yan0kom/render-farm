package rf.api.dto;

public class BaseOutDto {
	private Boolean success;

	public BaseOutDto(Boolean success) {
		this.success = success;
	}

	public BaseOutDto() {
		this(false);
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getError() {
		return null;
	}
}
