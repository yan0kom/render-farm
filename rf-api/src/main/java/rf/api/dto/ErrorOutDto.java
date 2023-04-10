package rf.api.dto;

public class ErrorOutDto extends BaseOutDto {
	private final String error;

	public ErrorOutDto(String error) {
		super(false);
		this.error = error;
	}

	public String getError() {
		return error;
	}
}
