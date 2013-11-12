package info.invertirenbolsa.fundamentales.exceptions;

public class CompanyNotFoundException extends FundamentalsException {

	private static final long serialVersionUID = 4999661171918271856L;

	public CompanyNotFoundException() {
		super(ExceptionsEnum.COMPANY_NOT_FOUND);
	}

}
