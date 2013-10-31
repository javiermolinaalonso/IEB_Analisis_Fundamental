package info.invertirenbolsa.fundamentales.exceptions;

public class FundamentalsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3700186678028661812L;
	private ExceptionsEnum code;
	
	public FundamentalsException(ExceptionsEnum code){
		super();
		this.code = code;
	}

	public ExceptionsEnum getCode() {
		return code;
	}

	public void setCode(ExceptionsEnum code) {
		this.code = code;
	}

}