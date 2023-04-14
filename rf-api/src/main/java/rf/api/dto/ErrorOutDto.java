package rf.api.dto;

public class ErrorOutDto extends BaseOutDto {
	private String error;

	public ErrorOutDto() {
		this(null);
	}

	public ErrorOutDto(String error) {
		super(false);
		this.error = error;
	}

	@Override
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
