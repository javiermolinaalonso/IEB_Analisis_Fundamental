package info.invertirenbolsa.fundamentales.exceptions;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1336071679503612850L;
	
	private String message;
	
	public ValidationException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
